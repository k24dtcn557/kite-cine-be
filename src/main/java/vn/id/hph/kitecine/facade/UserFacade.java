package vn.id.hph.kitecine.facade;

import java.text.ParseException;
import java.util.Objects;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.param.AuthenticationRequest;
import vn.id.hph.kitecine.controller.param.ChangePasswordParam;
import vn.id.hph.kitecine.controller.param.LogoutRequest;
import vn.id.hph.kitecine.controller.param.ResetPasswordParam;
import vn.id.hph.kitecine.controller.param.UserAvatarUpdateParam;
import vn.id.hph.kitecine.controller.param.UserCreationParam;
import vn.id.hph.kitecine.controller.param.UserProfileUpdateParam;
import vn.id.hph.kitecine.controller.param.UserRegistrationParam;
import vn.id.hph.kitecine.controller.param.UserSearchParam;
import vn.id.hph.kitecine.controller.param.UserUpdateRequest;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.facade.dto.AuthenticationResponse;
import vn.id.hph.kitecine.facade.dto.UserDto;
import vn.id.hph.kitecine.service.AuthenticationService;
import vn.id.hph.kitecine.service.NotificationService;
import vn.id.hph.kitecine.service.UserService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserFacade {
    UserService userService;
    NotificationService notificationService;
    private final AuthenticationService authenticationService;

    @PreAuthorize("hasRole('ADMIN')")
    public void resetUserPassword(String userId) {
        var user = userService.get(userId);
        var newPwd = userService.resetUserPassword(user);
        notificationService.sendNewPassword(user.getEmail(), newPwd);
    }

    @Transactional
    public void resetPassword(ResetPasswordParam request) {
        var user = userService.getByEmail(request.getEmail());
        if (Objects.nonNull(user)) {
            var newPwd = userService.resetUserPassword(user);
            notificationService.sendNewPassword(user.getEmail(), newPwd);
        }
    }

    @Transactional
    public UserDto register(UserRegistrationParam request) {
        var user = userService.register(request);

        notificationService.sendWelcomeOnboard(user.getEmail(), user.getFullName());

        return user;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto createUser(UserCreationParam param) {
        return userService.create(param);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<UserDto> searchUsers(UserSearchParam param) {
        return userService.searchUsers(param);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto getUser(String userId) {
        return userService.getUser(userId);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userService.deleteUser(userId);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto updateUser(String userId, UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto activateUser(String userId) {
        return userService.activateUser(userId);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto lockUser(String userId) {
        return userService.lockUser(userId);
    }

    public UserDto getMyInfo() {
        return userService.getMyInfo();
    }

    @Transactional
    public UserDto updateProfile(UserProfileUpdateParam param) {
        return userService.updateProfile(param);
    }

    @Transactional
    public UserDto changePassword(ChangePasswordParam param) {
        return userService.changePassword(param);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
    }

    public UserDto updateAvatar(UserAvatarUpdateParam param) {
        return userService.updateAvatar(param);
    }
}
