package vn.id.hph.kitecine.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import vn.id.hph.kitecine.controller.param.TicketParam;
import vn.id.hph.kitecine.entity.Ticket;
import vn.id.hph.kitecine.facade.dto.TicketDto;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    Ticket toTicket(TicketParam dto);

    TicketDto toTicketDto(Ticket entity);

    void update(@MappingTarget Ticket entity, TicketParam param);

    List<TicketDto> toTicketDtoList(List<Ticket> entities);
}
