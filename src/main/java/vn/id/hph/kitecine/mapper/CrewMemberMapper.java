package vn.id.hph.kitecine.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import vn.id.hph.kitecine.controller.param.CrewMemberParam;
import vn.id.hph.kitecine.entity.CrewMember;
import vn.id.hph.kitecine.facade.dto.CrewMemberDto;

@Mapper(componentModel = "spring")
public interface CrewMemberMapper {
    // map only simple fields from param; associations are set in the service
    CrewMember toCrewMember(CrewMemberParam param);

    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "crewPerson.id", target = "crewPersonId")
    @Mapping(source = "crewPerson.name", target = "name")
    @Mapping(source = "crewPerson.avatar", target = "avatar")
    CrewMemberDto toCrewMemberDto(CrewMember entity);

    void update(@MappingTarget CrewMember entity, CrewMemberParam param);

    List<CrewMemberDto> toCrewMemberDtoList(List<CrewMember> entities);
}
