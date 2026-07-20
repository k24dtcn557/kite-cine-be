package vn.id.hph.kitecine.facade;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.param.TicketParam;
import vn.id.hph.kitecine.facade.dto.TicketDto;
import vn.id.hph.kitecine.mapper.TicketMapper;
import vn.id.hph.kitecine.service.CinemaService;
import vn.id.hph.kitecine.service.SeatService;
import vn.id.hph.kitecine.service.ShowTimeService;
import vn.id.hph.kitecine.service.TicketService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookingFacade {
    TicketService ticketService;
    ShowTimeService showTimeService;
    SeatService seatService;
    CinemaService cinemaService;

    TicketMapper ticketMapper;

    public TicketDto reserve(TicketParam param) {
        var showTime = showTimeService.get(param.showtimeId());
        var seat = seatService.get(param.seatId());

        var ticket = ticketService.reserve(showTime, seat, param);

        return ticketMapper.toTicketDto(ticket);
    }

    public void delete(Long id) {
        ticketService.delete(id);
    }

    public TicketDto getTicket(Long id) {
        var ticket = ticketService.get(id);
        return ticketMapper.toTicketDto(ticket);
    }

    public List<TicketDto> getByShowTime(Long showtimeId) {
        var tickets = ticketService.getByShowtimeId(showtimeId);
        return ticketMapper.toTicketDtoList(tickets);
    }

    public List<TicketDto> getMyTickets() {
        var tickets = ticketService.getMyTickets();
        return ticketMapper.toTicketDtoList(tickets);
    }

    public List<TicketDto> getMyHoldings(Long showtimeId) {
        var tickets = ticketService.getMyHoldings(showtimeId);
        return ticketMapper.toTicketDtoList(tickets);
    }
}
