package vn.id.hph.kitecine.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import vn.id.hph.kitecine.controller.param.CinemaParam;
import vn.id.hph.kitecine.entity.Cinema;
import vn.id.hph.kitecine.facade.dto.CinemaDto;

@Mapper(componentModel = "spring")
public interface CinemaMapper {
    Cinema toCinema(CinemaParam param);

    CinemaDto toCinemaDto(Cinema entity);

    void update(@MappingTarget Cinema entity, CinemaParam param);

    List<CinemaDto> toCinemaDtoList(List<Cinema> entities);
}
