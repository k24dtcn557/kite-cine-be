package vn.id.hph.kitecine.facade;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.configuration.CommonUtils;
import vn.id.hph.kitecine.controller.param.DashboardReportParam;
import vn.id.hph.kitecine.facade.dto.DashboardQuickStatsDto;
import vn.id.hph.kitecine.service.AuditoriumService;
import vn.id.hph.kitecine.service.CinemaService;
import vn.id.hph.kitecine.service.ShowTimeService;
import vn.id.hph.kitecine.service.TicketService;
import vn.id.hph.kitecine.service.model.ChartColumnDto;
import vn.id.hph.kitecine.service.report.DashboardReportService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReportFacade {
    CinemaService cinemaService;
    AuditoriumService auditoriumService;
    ShowTimeService showTimeService;
    TicketService ticketService;
    List<DashboardReportService> dashboardReportServices;

    @PreAuthorize("hasRole('ADMIN')")
    public DashboardQuickStatsDto getDashboardQuickStats() {
        int totalCinemas = cinemaService.getNumberOfCinemas();
        int totalAuditoriums = auditoriumService.getNumberOfAuditoriums();
        int totalTicketsSold = ticketService.getSoldTicketsToday();
        int seatCount = showTimeService.getScheduledSeats(CommonUtils.getVietnamLocalDate());

        double fillRate = totalTicketsSold / (double) seatCount;
        BigDecimal revenue = ticketService.getRevenueToday();

        return new DashboardQuickStatsDto(revenue, totalCinemas, totalAuditoriums, totalTicketsSold, fillRate);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ChartColumnDto> getReport(DashboardReportParam param) {
        var reportService = dashboardReportServices.stream()
                .filter(service -> service.getTimeRange() == param.getType())
                .findFirst()
                .orElse(null);
        if (Objects.isNull(reportService)) {
            return Collections.emptyList();
        }
        return reportService.getReport(param).getData();
    }
}
