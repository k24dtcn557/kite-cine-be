package vn.id.hph.kitecine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.param.UserSearchParam;
import vn.id.hph.kitecine.controller.param.UserUpdateRequest;
import vn.id.hph.kitecine.controller.reponse.ApiResponse;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.facade.UserFacade;
import vn.id.hph.kitecine.facade.dto.UserDto;
import vn.id.hph.kitecine.service.UserService;

@RestController
@RequestMapping("/management/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserManagementController {
    UserFacade userFacade;
    UserService userService;

    @GetMapping
    ApiResponse<List<UserDto>> getUsers() {
        return ApiResponse.<List<UserDto>>builder()
                .result(userService.getUsers())
                .build();
    }

    @PostMapping("/search")
    ApiResponse<PageResponse<UserDto>> searchUsers(@RequestBody UserSearchParam param) {
        return ApiResponse.<PageResponse<UserDto>>builder()
                .result(userService.searchUsers(param))
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserDto> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserDto>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserDto> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserDto>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @PostMapping("/{userId}/reset-password")
    ApiResponse<Void> resetUserPassword(@PathVariable String userId) {
        userFacade.resetUserPassword(userId);
        return ApiResponse.<Void>builder()
                .build();
    }
}
