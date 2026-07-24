package vn.id.hph.kitecine.service.report;

import vn.id.hph.kitecine.controller.param.DashboardReportParam;
import vn.id.hph.kitecine.enums.TimeRange;
import vn.id.hph.kitecine.service.model.ChartReportDto;

public interface DashboardReportService {
    TimeRange getTimeRange();

    ChartReportDto getReport(DashboardReportParam event);
}
