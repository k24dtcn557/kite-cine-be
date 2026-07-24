package vn.id.hph.kitecine.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import vn.id.hph.kitecine.configuration.CommonUtils;
import vn.id.hph.kitecine.controller.param.TicketParam;
import vn.id.hph.kitecine.controller.param.TicketSearchParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.entity.Purchase;
import vn.id.hph.kitecine.entity.Seat;
import vn.id.hph.kitecine.entity.ShowTime;
import vn.id.hph.kitecine.entity.Ticket;
import vn.id.hph.kitecine.enums.SeatType;
import vn.id.hph.kitecine.enums.TicketStatus;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;
import vn.id.hph.kitecine.mapper.TicketMapper;
import vn.id.hph.kitecine.repository.SeatRepository;
import vn.id.hph.kitecine.repository.ShowTimeRepository;
import vn.id.hph.kitecine.repository.TicketRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketService {
    TicketRepository ticketRepository;
    TicketMapper ticketMapper;
    ShowTimeRepository showTimeRepository;
    SeatRepository seatRepository;

    @NonFinal
    final long reservedTime = 5; // In Minutes

    public Ticket reserve(ShowTime showTime, Seat seat, TicketParam param) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var ticket = ticketMapper.toTicket(param);

        String seatType = seat.getSeatType();
        BigDecimal price =
                showTime.getPriceModel().getPrices().getOrDefault(SeatType.valueOf(seatType), BigDecimal.ZERO);

        ticket.setShowtime(showTime);
        ticket.setSeat(seat);
        ticket.setPurchasePrice(price);
        ticket.setExpirationTime(Instant.now().plusSeconds(reservedTime * 60));
        ticket.setBuyerId(userId);
        ticket.setQrCode(CommonUtils.generateTicketCode());
        ticket.setStatus(TicketStatus.HOLD.name());

        return ticketRepository.save(ticket);
    }

    public Ticket update(Long id, TicketParam param) {
        var ticket = ticketRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_FOUND));

        ShowTime showTime = showTimeRepository
                .findById(param.showtimeId())
                .orElseThrow(() -> new AppException(ErrorCode.SHOW_TIME_NOT_FOUND));
        Seat seat =
                seatRepository.findById(param.seatId()).orElseThrow(() -> new AppException(ErrorCode.SEAT_NOT_FOUND));

        ticketMapper.update(ticket, param);
        ticket.setShowtime(showTime);
        ticket.setSeat(seat);

        return ticketRepository.save(ticket);
    }

    public PageResponse<Ticket> search(TicketSearchParam param) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(param.getPage(), param.getSize(), sort);

        Specification<Ticket> query = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(param.getShowtimeId())) {
                predicates.add(criteriaBuilder.equal(root.get("showtime").get("id"), param.getShowtimeId()));
            }

            if (Objects.nonNull(param.getSeatId())) {
                predicates.add(criteriaBuilder.equal(root.get("seat").get("id"), param.getSeatId()));
            }

            if (StringUtils.hasText(param.getBuyerId())) {
                predicates.add(criteriaBuilder.equal(root.get("buyerId"), param.getBuyerId()));
            }

            if (StringUtils.hasText(param.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"), param.getStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<Ticket> ticketPage = ticketRepository.findAll(query, pageRequest);
        return PageResponse.<Ticket>builder()
                .totalPages(ticketPage.getTotalPages())
                .pageNumber(ticketPage.getNumber())
                .totalElements(ticketPage.getTotalElements())
                .pageSize(ticketPage.getSize())
                .data(ticketPage.getContent())
                .build();
    }

    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    public Ticket get(Long id) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var ticket = ticketRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_FOUND));
        if (!ticket.getBuyerId().equals(userId)) {
            throw new AppException(ErrorCode.TICKET_NOT_FOUND);
        }
        return ticket;
    }

    public void delete(Long id) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var ticket = ticketRepository.findById(id).orElse(null);
        if (ticket == null || !ticket.getBuyerId().equals(userId)) {
            return;
        }

        ticketRepository.delete(ticket);
    }

    public List<Ticket> getByShowtimeId(Long showtimeId) {
        return ticketRepository.findByShowtime_IdAndStatusIn(
                showtimeId, List.of(TicketStatus.HOLD.name(), TicketStatus.CONFIRMED.name()));
    }

    public List<Ticket> getMyHoldings(long showTimeId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ticketRepository.findByShowtime_IdAndBuyerIdAndStatusIn(
                showTimeId, userId, List.of(TicketStatus.HOLD.name()));
    }

    public List<Ticket> getMyTickets() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ticketRepository.findByBuyerIdAndStatusIn(
                userId, List.of(TicketStatus.CANCELLED.name(), TicketStatus.CONFIRMED.name()));
    }

    public List<Ticket> getHoldingsByIds(List<Long> ids) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ticketRepository.findByBuyerIdAndStatusAndIdIn(userId, TicketStatus.HOLD.name(), ids);
    }

    public List<Ticket> initializePurchase(Purchase purchase, List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            ticket.setExpirationTime(purchase.getExpirationTime());
            ticket.setPurchase(purchase);
        }

        return ticketRepository.saveAll(tickets);
    }

    public List<Ticket> getHoldingsPurchaseId(Long purchaseId) {
        return ticketRepository.findByPurchase_IdAndStatus(purchaseId, TicketStatus.HOLD.name());
    }

    public List<Ticket> getByPurchaseId(Long purchaseId) {
        return ticketRepository.findByPurchase_Id(purchaseId);
    }

    public List<Ticket> pay(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            ticket.setStatus(TicketStatus.CONFIRMED.name());
        }
        return ticketRepository.saveAll(tickets);
    }

    public int getSoldTicketsToday() {
        LocalDate today = CommonUtils.getVietnamLocalDate();
        return ticketRepository.countByStatusAndShowtime_Date(TicketStatus.CONFIRMED.name(), today);
    }

    public BigDecimal getRevenueToday() {
        LocalDate today = CommonUtils.getVietnamLocalDate();
        return ticketRepository.sumPurchasePriceByStatusAndShowtimeDate(TicketStatus.CONFIRMED.name(), today);
    }

    public BigDecimal getRevenueByDay(LocalDate date) {
        return ticketRepository.sumPurchasePriceByStatusAndShowtimeDate(TicketStatus.CONFIRMED.name(), date);
    }

    public BigDecimal getRevenueByDays(LocalDate fromDate, LocalDate toDate) {
        return ticketRepository.sumPurchasePriceByStatusAndShowtimeDates(
                TicketStatus.CONFIRMED.name(), fromDate, toDate);
    }

    public BigDecimal getRevenueByMovie(Long id) {
        return ticketRepository.sumPurchasePriceByStatusAndMovieId(TicketStatus.CONFIRMED.name(), id);
    }

    public BigDecimal getRevenueByCinema(Long id, LocalDate fromDate, LocalDate toDate) {
        return ticketRepository.sumPurchasePriceByStatusAndCinemaIdAndShowtimeDates(
                TicketStatus.CONFIRMED.name(), id, fromDate, toDate);
    }
}
