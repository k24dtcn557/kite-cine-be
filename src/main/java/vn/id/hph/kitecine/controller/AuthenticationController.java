package vn.id.hph.kitecine.controller;

import java.text.ParseException;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.id.hph.kitecine.controller.param.AuthenticationRequest;
import vn.id.hph.kitecine.controller.param.ChangePasswordParam;
import vn.id.hph.kitecine.controller.param.LogoutRequest;
import vn.id.hph.kitecine.controller.param.ResetPasswordParam;
import vn.id.hph.kitecine.controller.param.UserProfileUpdateParam;
import vn.id.hph.kitecine.controller.param.UserRegistrationParam;
import vn.id.hph.kitecine.controller.reponse.ApiResponse;
import vn.id.hph.kitecine.facade.UserFacade;
import vn.id.hph.kitecine.facade.dto.AuthenticationResponse;
import vn.id.hph.kitecine.facade.dto.UserDto;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    UserFacade userFacade;

    @PostMapping("/users/register")
    ApiResponse<UserDto> createUser(@RequestBody @Valid UserRegistrationParam request) {
        return ApiResponse.<UserDto>builder()
                .result(userFacade.register(request))
                .build();
    }

    @PostMapping("/users/reset-password")
    ApiResponse<Void> resetPassword(@RequestBody ResetPasswordParam request) {
        userFacade.resetPassword(request);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/users/my-info")
    ApiResponse<UserDto> getMyInfo() {
        return ApiResponse.<UserDto>builder().result(userFacade.getMyInfo()).build();
    }

    @PutMapping("/users/my-info")
    ApiResponse<UserDto> updateUser(@RequestBody UserProfileUpdateParam param) {
        return ApiResponse.<UserDto>builder()
                .result(userFacade.updateProfile(param))
                .build();
    }

    @PostMapping("/users/change-password")
    ApiResponse<UserDto> changePassword(@RequestBody ChangePasswordParam param) {
        return ApiResponse.<UserDto>builder()
                .result(userFacade.changePassword(param))
                .build();
    }

    @PostMapping("/auth/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = userFacade.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/auth/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        userFacade.logout(request);
        return ApiResponse.<Void>builder().build();
    }
}
