package vn.id.hph.kitecine.controller;

import org.springframework.web.bind.annotation.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.param.UserCreationParam;
import vn.id.hph.kitecine.controller.param.UserSearchParam;
import vn.id.hph.kitecine.controller.param.UserUpdateRequest;
import vn.id.hph.kitecine.controller.reponse.ApiResponse;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.facade.UserFacade;
import vn.id.hph.kitecine.facade.dto.UserDto;

@RestController
@RequestMapping("/management/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserManagementController {
    UserFacade userFacade;

    @PostMapping
    ApiResponse<UserDto> createUser(@RequestBody UserCreationParam param) {
        return ApiResponse.<UserDto>builder()
                .result(userFacade.createUser(param))
                .build();
    }

    @PostMapping("/search")
    ApiResponse<PageResponse<UserDto>> searchUsers(@RequestBody UserSearchParam param) {
        return ApiResponse.<PageResponse<UserDto>>builder()
                .result(userFacade.searchUsers(param))
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserDto> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserDto>builder().result(userFacade.getUser(userId)).build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userFacade.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserDto> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserDto>builder()
                .result(userFacade.updateUser(userId, request))
                .build();
    }

    @PostMapping("/{userId}/lock")
    ApiResponse<UserDto> lockUser(@PathVariable String userId) {
        return ApiResponse.<UserDto>builder()
                .result(userFacade.lockUser(userId))
                .build();
    }

    @PostMapping("/{userId}/activate")
    ApiResponse<UserDto> activateUser(@PathVariable String userId) {
        return ApiResponse.<UserDto>builder()
                .result(userFacade.activateUser(userId))
                .build();
    }

    @PostMapping("/{userId}/reset-password")
    ApiResponse<Void> resetUserPassword(@PathVariable String userId) {
        userFacade.resetUserPassword(userId);
        return ApiResponse.<Void>builder().build();
    }
}
