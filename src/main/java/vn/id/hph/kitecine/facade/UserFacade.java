package vn.id.hph.kitecine.facade;

import java.util.Objects;

import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.param.ResetPasswordParam;
import vn.id.hph.kitecine.controller.param.UserRegistrationParam;
import vn.id.hph.kitecine.controller.param.UserSearchParam;
import vn.id.hph.kitecine.controller.param.UserUpdateRequest;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.facade.dto.UserDto;
import vn.id.hph.kitecine.service.NotificationService;
import vn.id.hph.kitecine.service.UserService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserFacade {
    UserService userService;
    NotificationService notificationService;

    @PreAuthorize("hasRole('ADMIN')")
    public void resetUserPassword(String userId) {
        var user = userService.get(userId);
        var newPwd = userService.resetUserPassword(user);
        notificationService.sendNewPassword(user.getEmail(), newPwd);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void resetPassword(ResetPasswordParam request) {
        var user = userService.getByEmail(request.getEmail());
        if (Objects.nonNull(user)) {
            var newPwd = userService.resetUserPassword(user);
            notificationService.sendNewPassword(user.getEmail(), newPwd);
        }
    }

    public UserDto register(UserRegistrationParam request) {
        var user = userService.register(request);

        notificationService.sendWelcomeOnboard(user.getEmail(), user.getFullName());

        return user;
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
}
