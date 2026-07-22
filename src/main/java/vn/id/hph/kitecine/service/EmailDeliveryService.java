package vn.id.hph.kitecine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.configuration.EmailProperties;
import vn.id.hph.kitecine.enums.PayloadField;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;
import vn.id.hph.kitecine.repository.adapter.BrevoClient;
import vn.id.hph.kitecine.repository.model.EmailRequest;
import vn.id.hph.kitecine.repository.model.Recipient;
import vn.id.hph.kitecine.repository.model.Sender;
import vn.id.hph.kitecine.service.model.NotificationDeliveryParam;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailDeliveryService {
    BrevoClient brevoClient;

    @Value("${email-services.brevo.apikey}")
    @NonFinal
    String apiKey;

    EmailProperties emailProperties;

    public String deliver(NotificationDeliveryParam param) {
        if (!StringUtils.hasText(param.getRecipient())) {
            throw new AppException(ErrorCode.NO_EMAIL_ADDRESS);
        }

        var emailSender = emailProperties.getSenders().getFirst();

        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name(emailSender.senderName())
                        .email(emailSender.senderEmail())
                        .build())
                .to(List.of(Recipient.builder().email(param.getRecipient()).build()))
                .subject(param.getPayload().get(PayloadField.TITLE.getValue()))
                .htmlContent(param.getPayload().get(PayloadField.CONTENT.getValue()))
                .build();
        try {
            var emailResponse = brevoClient.sendEmail(apiKey, emailRequest);
            return emailResponse.getMessageId();
        } catch (FeignException e) {
            log.error("Error while sending email", e);
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }
}
