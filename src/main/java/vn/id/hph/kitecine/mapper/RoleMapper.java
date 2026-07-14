package vn.id.hph.kitecine.mapper;

import org.mapstruct.Mapper;

import vn.id.hph.kitecine.controller.param.RoleRequest;
import vn.id.hph.kitecine.entity.Role;
import vn.id.hph.kitecine.facade.dto.RoleResponse;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
