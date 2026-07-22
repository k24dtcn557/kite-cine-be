package vn.id.hph.kitecine.repository.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import vn.id.hph.kitecine.repository.model.EmailRequest;
import vn.id.hph.kitecine.repository.model.EmailResponse;

@FeignClient(name = "email-client", url = "${email-services.brevo.url}")
public interface BrevoClient {
    @PostMapping(value = "/v3/smtp/email", produces = MediaType.APPLICATION_JSON_VALUE)
    EmailResponse sendEmail(@RequestHeader("api-key") String apiKey, @RequestBody EmailRequest body);
}
