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
import vn.id.hph.kitecine.enums.TimeRange;
import vn.id.hph.kitecine.service.TicketService;
import vn.id.hph.kitecine.service.model.ChartColumnDto;
import vn.id.hph.kitecine.service.model.ChartReportDto;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TweleMonthsReportService implements DashboardReportService {
    TicketService ticketService;

    @Override
    public TimeRange getTimeRange() {
        return TimeRange.ONE_YEAR;
    }

    @Override
    public ChartReportDto getReport(DashboardReportParam param) {
        List<ChartColumnDto> chart = generateChart(param);

        return ChartReportDto.builder().data(chart).build();
    }

    private List<ChartColumnDto> generateChart(DashboardReportParam param) {
        // Generate chart columns for the current month
        List<ChartColumnDto> chart = new ArrayList<>();
        var timeSeries = generateTimeSeries();

        for (LocalDate date : timeSeries) {
            BigDecimal revenue =
                    ticketService.getRevenueByDays(date, date.plusMonths(1).minusDays(1));

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
        LocalDate firstMonth = LocalDate.of(
                CommonUtils.getVietnamLocalDate().getYear(),
                CommonUtils.getVietnamLocalDate().getMonth(),
                1);

        List<LocalDate> timeSeries = new ArrayList<>();
        timeSeries.add(firstMonth);

        for (int i = 1; i <= 11; i++) {
            timeSeries.add(firstMonth.minusMonths(i));
        }

        return timeSeries.reversed();
    }

    private String generateLabel(LocalDate date) {
        return CommonUtils.formatMonth(date);
    }
}
