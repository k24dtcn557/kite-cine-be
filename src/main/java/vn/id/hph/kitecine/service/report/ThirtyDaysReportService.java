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
import vn.id.hph.kitecine.enums.ReportType;
import vn.id.hph.kitecine.service.TicketService;
import vn.id.hph.kitecine.service.model.ChartColumnDto;
import vn.id.hph.kitecine.service.model.ChartReportDto;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThirtyDaysReportService implements DashboardReportService {
    TicketService ticketService;

    @Override
    public ReportType getTimeRange() {
        return ReportType.THIRTY_DAYS;
    }

    @Override
    public ChartReportDto getReport(DashboardReportParam param) {
        List<ChartColumnDto> chart = generateChart(param);

        return ChartReportDto.builder().data(chart).build();
    }

    private List<ChartColumnDto> generateChart(DashboardReportParam param) {
        // Generate chart columns for the current day
        List<ChartColumnDto> chart = new ArrayList<>();
        var timeSeries = generateTimeSeries();

        for (LocalDate date : timeSeries) {
            BigDecimal revenue = ticketService.getRevenueByDay(date);

            ChartColumnDto chartColumn = ChartColumnDto.builder()
                    .label(generateLabel(date))
                    .value(Objects.isNull(revenue) ? 0 : revenue.longValue())
                    .build();

            chart.add(chartColumn);
        }

        return chart;
    }

    // Generate time series for the report
    private List<LocalDate> generateTimeSeries() {
        LocalDate today = CommonUtils.getVietnamLocalDate();
        List<LocalDate> timeSeries = new ArrayList<>();
        timeSeries.add(today);

        for (int i = 1; i <= 30; i++) {
            timeSeries.add(today.minusDays(i));
        }

        return timeSeries.reversed();
    }

    private String generateLabel(LocalDate date) {
        return CommonUtils.formatDayMonth(date);
    }
}
