package vn.id.hph.kitecine.facade;

import java.math.BigDecimal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.facade.dto.DashboardQuickStatsDto;
import vn.id.hph.kitecine.service.AuditoriumService;
import vn.id.hph.kitecine.service.CinemaService;
import vn.id.hph.kitecine.service.SeatService;
import vn.id.hph.kitecine.service.TicketService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReportFacade {
    CinemaService cinemaService;
    AuditoriumService auditoriumService;
    TicketService ticketService;
    SeatService seatService;

    @PreAuthorize("hasRole('ADMIN')")
    public DashboardQuickStatsDto getDashboardQuickStats() {
        int totalCinemas = cinemaService.getNumberOfCinemas();
        int totalAuditoriums = auditoriumService.getNumberOfAuditoriums();
        int totalTicketsSold = ticketService.getSoldTicketsToday();
        int seatCount = seatService.getSeatCount();

        double fillRate = totalTicketsSold / (double) seatCount;
        BigDecimal revenue = ticketService.getRevenueToday();

        return new DashboardQuickStatsDto(revenue, totalCinemas, totalAuditoriums, seatCount, fillRate);
    }
}
