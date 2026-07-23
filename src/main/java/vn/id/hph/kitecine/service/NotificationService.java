package vn.id.hph.kitecine.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.enums.EmailTemplate;
import vn.id.hph.kitecine.enums.PayloadField;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;
import vn.id.hph.kitecine.repository.EmailDeliveryRepository;
import vn.id.hph.kitecine.service.model.NotificationDeliveryParam;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationService {
    EmailDeliveryRepository emailDeliveryRepository;

    public void sendNewPassword(String email, String newPassword) {
        String template = loadTemplate(EmailTemplate.RESET_PASSWORD);
        String content = applyParams(template, Map.of("NEW_PASSWORD", newPassword));

        Map<String, String> payload =
                Map.of(PayloadField.TITLE.getValue(), "Password mới của bạn", PayloadField.CONTENT.getValue(), content);

        emailDeliveryRepository.deliver(NotificationDeliveryParam.builder()
                .recipient(email)
                .payload(payload)
                .build());
    }

    private String applyParams(String template, Map<String, String> params) {
        StringSubstitutor sub = new StringSubstitutor(params);

        return sub.replace(template);
    }

    private String loadTemplate(EmailTemplate template) {
        try (InputStream is = new ClassPathResource("email_templates/" + template.name() + ".html").getInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Failed to load template {}", template.name(), e);
            throw new AppException(ErrorCode.CANNOT_READ_TEMPLATE);
        }
    }
}
