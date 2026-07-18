package vn.id.hph.kitecine.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import vn.id.hph.kitecine.controller.param.CrewPersonParam;
import vn.id.hph.kitecine.entity.CrewPerson;
import vn.id.hph.kitecine.facade.dto.CrewPersonDto;

@Mapper(componentModel = "spring")
public interface CrewPersonMapper {
    CrewPerson toCrewPerson(CrewPersonParam param);

    CrewPersonDto toCrewPersonDto(CrewPerson entity);

    void update(@MappingTarget CrewPerson entity, CrewPersonParam param);

    List<CrewPersonDto> toCrewPersonDtoList(List<CrewPerson> entities);
}
