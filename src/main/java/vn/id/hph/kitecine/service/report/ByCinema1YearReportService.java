package vn.id.hph.kitecine.service.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.configuration.CommonUtils;
import vn.id.hph.kitecine.controller.param.DashboardReportParam;
import vn.id.hph.kitecine.entity.Cinema;
import vn.id.hph.kitecine.enums.ReportType;
import vn.id.hph.kitecine.service.CinemaService;
import vn.id.hph.kitecine.service.TicketService;
import vn.id.hph.kitecine.service.model.ChartColumnDto;
import vn.id.hph.kitecine.service.model.ChartReportDto;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ByCinema1YearReportService implements DashboardReportService {
    TicketService ticketService;
    CinemaService cinemaService;

    @Override
    public ReportType getTimeRange() {
        return ReportType.BY_CINEMA_1_YEAR;
    }

    @Override
    public ChartReportDto getReport(DashboardReportParam param) {
        List<ChartColumnDto> chart = generateChart(param);

        return ChartReportDto.builder().data(chart).build();
    }

    private List<ChartColumnDto> generateChart(DashboardReportParam param) {
        // Generate chart columns for 1 year
        List<ChartColumnDto> chart = new ArrayList<>();
        var timeSeries = generateTimeSeries();
        LocalDate today = CommonUtils.getVietnamLocalDate();
        LocalDate previousDate = today.minusYears(1);
        for (Cinema cinema : timeSeries) {
            BigDecimal revenue = ticketService.getRevenueByCinema(cinema.getId(), previousDate, today);

            ChartColumnDto chartColumn = ChartColumnDto.builder()
                    .label(generateLabel(cinema))
                    .value(Objects.isNull(revenue) ? 0 : revenue.longValue())
                    .build();

            chart.add(chartColumn);
        }

        return chart;
    }

    // Generate time series for the report
    private List<Cinema> generateTimeSeries() {
        return cinemaService.getAll();
    }

    private String generateLabel(Cinema cinema) {
        return cinema.getName();
    }
}
