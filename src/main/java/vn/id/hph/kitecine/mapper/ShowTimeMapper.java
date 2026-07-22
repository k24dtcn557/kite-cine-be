package vn.id.hph.kitecine.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import vn.id.hph.kitecine.controller.param.ShowTimeParam;
import vn.id.hph.kitecine.entity.ShowTime;
import vn.id.hph.kitecine.facade.dto.ShowTimeBriefDto;
import vn.id.hph.kitecine.facade.dto.ShowTimeDetailDto;
import vn.id.hph.kitecine.facade.dto.ShowTimeDto;

@Mapper(componentModel = "spring")
public interface ShowTimeMapper {
    ShowTime toShowTime(ShowTimeParam param);

    @Mapping(source = "auditorium.id", target = "auditoriumId")
    @Mapping(source = "priceModel.id", target = "priceModelId")
    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "movie.title", target = "movieTitle")
    ShowTimeDto toShowTimeDto(ShowTime entity);

    ShowTimeBriefDto toShowTimeBriefDto(ShowTime entity);

    void update(@MappingTarget ShowTime entity, ShowTimeParam param);

    List<ShowTimeDto> toShowTimeDtoList(List<ShowTime> entities);

    ShowTimeDetailDto toShowTimeDetailDto(ShowTime showTime);

    List<ShowTimeDetailDto> toShowTimeDetailDtoList(List<ShowTime> showTimes);
}
