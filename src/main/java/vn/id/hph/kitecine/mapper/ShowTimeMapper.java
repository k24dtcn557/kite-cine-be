package vn.id.hph.kitecine.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import vn.id.hph.kitecine.controller.param.ShowTimeParam;
import vn.id.hph.kitecine.entity.ShowTime;
import vn.id.hph.kitecine.facade.dto.ShowTimeDto;

@Mapper(componentModel = "spring")
public interface ShowTimeMapper {
    @Mapping(source = "movieId", target = "movieId")
    ShowTime toShowTime(ShowTimeParam param);

    @Mapping(source = "auditorium.id", target = "auditoriumId")
    @Mapping(source = "priceModel.id", target = "priceModelId")
    ShowTimeDto toShowTimeDto(ShowTime entity);

    void update(@MappingTarget ShowTime entity, ShowTimeParam param);

    List<ShowTimeDto> toShowTimeDtoList(List<ShowTime> entities);
}
