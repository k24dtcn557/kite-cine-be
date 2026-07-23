package vn.id.hph.kitecine.facade;

import java.util.Objects;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.param.ResetPasswordParam;
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

    public void resetPassword(ResetPasswordParam request) {
        var user = userService.getByEmail(request.getEmail());
        if (Objects.nonNull(user)) {
            var newPwd = userService.resetUserPassword(user);
            notificationService.sendNewPassword(user.getEmail(), newPwd);
        }
    }
}
