package vn.id.hph.kitecine.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import vn.id.hph.kitecine.controller.param.AuditoriumParam;
import vn.id.hph.kitecine.entity.Auditorium;
import vn.id.hph.kitecine.facade.dto.AuditoriumDto;
import vn.id.hph.kitecine.facade.dto.AuditoriumWithCinemaDto;

@Mapper(componentModel = "spring")
public interface AuditoriumMapper {
    Auditorium toAuditorium(AuditoriumParam param);

    AuditoriumDto toAuditoriumDto(Auditorium entity);

    AuditoriumWithCinemaDto toAuditoriumWithCinemaDto(Auditorium entity);

    void update(@MappingTarget Auditorium entity, AuditoriumParam param);

    List<AuditoriumDto> toAuditoriumDtoList(List<Auditorium> entities);
}
