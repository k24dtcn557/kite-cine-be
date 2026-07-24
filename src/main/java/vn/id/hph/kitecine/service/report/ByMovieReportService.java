package vn.id.hph.kitecine.service.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.param.DashboardReportParam;
import vn.id.hph.kitecine.entity.Movie;
import vn.id.hph.kitecine.enums.TimeRange;
import vn.id.hph.kitecine.service.MovieService;
import vn.id.hph.kitecine.service.TicketService;
import vn.id.hph.kitecine.service.model.ChartColumnDto;
import vn.id.hph.kitecine.service.model.ChartReportDto;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ByMovieReportService implements DashboardReportService {
    TicketService ticketService;
    MovieService movieService;

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

        for (Movie movie : timeSeries) {
            BigDecimal revenue = ticketService.getRevenueByMovie(movie.getId());

            ChartColumnDto chartColumn = ChartColumnDto.builder()
                    .label(generateLabel(movie))
                    .value(Objects.isNull(revenue) ? 0 : revenue.longValue())
                    .build();

            chart.add(chartColumn);
        }

        return chart;
    }

    // Generate time series for the report
    private List<Movie> generateTimeSeries() {
        return movieService.getNowShowingMovies(null);
    }

    private String generateLabel(Movie movie) {
        return movie.getTitle();
    }
}
