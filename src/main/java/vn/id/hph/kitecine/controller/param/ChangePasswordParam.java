package vn.id.hph.kitecine.controller.param;

import jakarta.validation.constraints.Min;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePasswordParam {
    String oldPassword;

    @Min(value = 8, message = "INVALID_PASSWORD")
    String newPassword;

    @Min(value = 8, message = "INVALID_PASSWORD")
    String confirmNewPassword;
}
