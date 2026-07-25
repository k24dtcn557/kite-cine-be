package vn.id.hph.kitecine.facade;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.param.BookingSearchParam;
import vn.id.hph.kitecine.controller.param.PurchaseParam;
import vn.id.hph.kitecine.controller.param.TicketParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.entity.Ticket;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;
import vn.id.hph.kitecine.facade.dto.PurchaseDetailDto;
import vn.id.hph.kitecine.facade.dto.PurchaseDto;
import vn.id.hph.kitecine.facade.dto.PurchaseWithShowTimeDto;
import vn.id.hph.kitecine.facade.dto.TicketDto;
import vn.id.hph.kitecine.mapper.PurchaseMapper;
import vn.id.hph.kitecine.mapper.ShowTimeMapper;
import vn.id.hph.kitecine.mapper.TicketMapper;
import vn.id.hph.kitecine.service.NotificationService;
import vn.id.hph.kitecine.service.PurchaseService;
import vn.id.hph.kitecine.service.SeatService;
import vn.id.hph.kitecine.service.ShowTimeService;
import vn.id.hph.kitecine.service.TicketService;
import vn.id.hph.kitecine.service.UserService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookingFacade {
    TicketService ticketService;
    ShowTimeService showTimeService;
    SeatService seatService;
    PurchaseService purchaseService;

    TicketMapper ticketMapper;
    PurchaseMapper purchaseMapper;
    ShowTimeMapper showTimeMapper;
    private final UserService userService;
    private final NotificationService notificationService;

    @Transactional
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

    @Transactional
    public PurchaseDto initializeBooking(PurchaseParam param) {
        var tickets = ticketService.getHoldingsByIds(param.ticketIds());
        if (CollectionUtils.isEmpty(tickets)) {
            throw new AppException(ErrorCode.TICKET_NOT_FOUND);
        }

        if (tickets.size() != param.ticketIds().size()) {
            throw new AppException(ErrorCode.TICKET_EXPIRED);
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Ticket ticket : tickets) {
            totalPrice = totalPrice.add(ticket.getPurchasePrice());
        }

        var purchase = purchaseService.initializePurchase(tickets.getFirst().getShowtime(), totalPrice);

        ticketService.initializePurchase(purchase, tickets);

        return purchaseMapper.toPurchaseDto(purchase);
    }

    @Transactional
    public PurchaseDetailDto payBooking(String code) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var purchase = purchaseService.get(code);
        var tickets = ticketService.getHoldingsPurchaseId(purchase.getId());
        if (tickets.isEmpty()) {
            throw new AppException(ErrorCode.TICKET_EXPIRED);
        }

        purchase = purchaseService.pay(purchase);
        tickets = ticketService.pay(tickets);

        var purchaseDto = purchaseMapper.toPurchaseDetailDto(purchase);
        purchaseDto.setTickets(ticketMapper.toTicketDtoList(tickets));

        var user = userService.get(userId);
        var purchaseWithShowTimeDto = purchaseMapper.toPurchaseWithShowTimeDto(purchase);
        purchaseWithShowTimeDto.setTickets(ticketMapper.toTicketDtoList(tickets));
        notificationService.sendTicket(user.getEmail(), purchaseWithShowTimeDto);

        return purchaseDto;
    }

    public List<PurchaseWithShowTimeDto> getUpcomingBookings() {
        var bookings = purchaseService.getUpComingBookings();
        return bookings.stream()
                .map(purchase -> {
                    var purchaseDto = purchaseMapper.toPurchaseWithShowTimeDto(purchase);

                    var tickets = ticketService.getByPurchaseId(purchase.getId());
                    purchaseDto.setTickets(ticketMapper.toTicketDtoList(tickets));
                    return purchaseDto;
                })
                .toList();
    }

    public List<PurchaseWithShowTimeDto> getPastBookings() {
        var bookings = purchaseService.getPastBookings();
        return bookings.stream()
                .map(purchase -> {
                    var purchaseDto = purchaseMapper.toPurchaseWithShowTimeDto(purchase);

                    var tickets = ticketService.getByPurchaseId(purchase.getId());
                    purchaseDto.setTickets(ticketMapper.toTicketDtoList(tickets));
                    return purchaseDto;
                })
                .toList();
    }

    public PurchaseWithShowTimeDto getByCode(String code) {
        var purchase = purchaseService.get(code);
        var purchaseDto = purchaseMapper.toPurchaseWithShowTimeDto(purchase);

        var tickets = ticketService.getByPurchaseId(purchase.getId());
        purchaseDto.setTickets(ticketMapper.toTicketDtoList(tickets));

        return purchaseDto;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<PurchaseWithShowTimeDto> searchBookings(BookingSearchParam param) {
        return purchaseService.searchBookings(param);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void cancelBooking(String code) {
        // TODO: Refund
        var purchase = purchaseService.cancel(code);
        var tickets = ticketService.getByPurchaseId(purchase.getId());
        ticketService.cancel(tickets);
    }
}
