package vn.id.hph.kitecine.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import vn.id.hph.kitecine.controller.param.SeatParam;
import vn.id.hph.kitecine.entity.Seat;
import vn.id.hph.kitecine.facade.dto.SeatDto;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    Seat toSeat(SeatParam dto);

    SeatDto toSeatDto(Seat entity);

    void update(@MappingTarget Seat entity, SeatParam param);

    List<SeatDto> toSeatDtoList(List<Seat> entities);
}
