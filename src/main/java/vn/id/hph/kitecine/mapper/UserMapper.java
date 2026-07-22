package vn.id.hph.kitecine.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import vn.id.hph.kitecine.controller.param.UserCreationRequest;
import vn.id.hph.kitecine.controller.param.UserProfileUpdateParam;
import vn.id.hph.kitecine.controller.param.UserUpdateRequest;
import vn.id.hph.kitecine.entity.User;
import vn.id.hph.kitecine.facade.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserDto toUserDto(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    void updateUser(@MappingTarget User user, UserProfileUpdateParam param);
}
