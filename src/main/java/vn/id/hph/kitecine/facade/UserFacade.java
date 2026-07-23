package vn.id.hph.kitecine.facade;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.service.NotificationService;
import vn.id.hph.kitecine.service.UserService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserFacade {
    UserService userService;
    NotificationService notificationService;

    public void resetUserPassword(String userId) {
        var user = userService.get(userId);
        var newPwd = userService.resetUserPassword(user);
        notificationService.sendNewPassword("hung.hp666@gmail.com", newPwd);
    }
}
