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
import vn.id.hph.kitecine.configuration.CommonUtils;
import vn.id.hph.kitecine.enums.EmailTemplate;
import vn.id.hph.kitecine.enums.PayloadField;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;
import vn.id.hph.kitecine.facade.dto.PurchaseWithShowTimeDto;
import vn.id.hph.kitecine.repository.EmailDeliveryRepository;
import vn.id.hph.kitecine.service.model.NotificationDeliveryParam;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationService {
    EmailDeliveryRepository emailDeliveryRepository;

    public void sendWelcomeOnboard(String email, String fullName) {
        String template = loadTemplate(EmailTemplate.WELCOME_ONBOARD);
        String content = applyParams(template, Map.of("FULL_NAME", fullName));

        Map<String, String> payload = Map.of(
                PayloadField.TITLE.getValue(),
                "Chào mừng bạn đến với KiteCine",
                PayloadField.CONTENT.getValue(),
                content);

        emailDeliveryRepository.deliver(NotificationDeliveryParam.builder()
                .recipient(email)
                .payload(payload)
                .build());
    }

    public void sendNewPassword(String email, String newPassword) {
        String template = loadTemplate(EmailTemplate.RESET_PASSWORD);
        String content = applyParams(template, Map.of("NEW_PASSWORD", newPassword));

        Map<String, String> payload =
                Map.of(PayloadField.TITLE.getValue(), "Mật khẩu mới của bạn", PayloadField.CONTENT.getValue(), content);

        emailDeliveryRepository.deliver(NotificationDeliveryParam.builder()
                .recipient(email)
                .payload(payload)
                .build());
    }

    public void sendTicket(String email, PurchaseWithShowTimeDto purchase) {
        String template = loadTemplate(EmailTemplate.TICKET_EMAIL);
        String seats = purchase.getTickets().stream()
                .map(ticket -> ticket.rowLetter() + ticket.seatNumber())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("");
        String content = applyParams(
                template,
                Map.of(
                        "MOVIE_TITLE", purchase.getShowtime().movie().title(),
                        "MOVIE_POSTER_URL", purchase.getShowtime().movie().poster(),
                        "SHOW_DATE",
                                CommonUtils.formatDate(purchase.getShowtime().date()),
                        "SHOW_TIME", purchase.getShowtime().startTime().toString(),
                        "CINEMA_NAME",
                                purchase.getShowtime().auditorium().cinema().name(),
                        "AUDITORIUM_NAME", purchase.getShowtime().auditorium().name(),
                        "SEATS", seats,
                        "QR_CODE_IMAGE_URL",
                                "https://kitecineapi.hph.id.vn/kite-cine/media/qrcode/" + purchase.getCode(),
                        "RESERVATION_CODE", purchase.getCode(),
                        "GRAND_TOTAL", CommonUtils.formatNumber(purchase.getGrandTotal())));

        Map<String, String> payload =
                Map.of(PayloadField.TITLE.getValue(), "Vé xem phim của bạn", PayloadField.CONTENT.getValue(), content);

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
