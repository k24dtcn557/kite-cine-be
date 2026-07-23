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
import vn.id.hph.kitecine.controller.param.UserCreationRequest;
import vn.id.hph.kitecine.controller.param.UserProfileUpdateParam;
import vn.id.hph.kitecine.controller.reponse.ApiResponse;
import vn.id.hph.kitecine.facade.UserFacade;
import vn.id.hph.kitecine.facade.dto.AuthenticationResponse;
import vn.id.hph.kitecine.facade.dto.UserDto;
import vn.id.hph.kitecine.service.AuthenticationService;
import vn.id.hph.kitecine.service.UserService;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    UserFacade userFacade;
    AuthenticationService authenticationService;
    UserService userService;

    @PostMapping("/users/register")
    ApiResponse<UserDto> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserDto>builder()
                .result(userService.createUser(request))
                .build();
    }

    @PostMapping("/users/reset-password")
    ApiResponse<Void> resetPassword(@RequestBody ResetPasswordParam request) {
        userFacade.resetPassword(request);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/users/my-info")
    ApiResponse<UserDto> getMyInfo() {
        return ApiResponse.<UserDto>builder().result(userService.getMyInfo()).build();
    }

    @PutMapping("/users/my-info")
    ApiResponse<UserDto> updateUser(@RequestBody UserProfileUpdateParam param) {
        return ApiResponse.<UserDto>builder()
                .result(userService.updateProfile(param))
                .build();
    }

    @PostMapping("/users/change-password")
    ApiResponse<UserDto> changePassword(@RequestBody ChangePasswordParam param) {
        return ApiResponse.<UserDto>builder()
                .result(userService.changePassword(param))
                .build();
    }

    @PostMapping("/auth/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/auth/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }
}
