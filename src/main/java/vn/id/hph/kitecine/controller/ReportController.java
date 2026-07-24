package vn.id.hph.kitecine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.param.DashboardReportParam;
import vn.id.hph.kitecine.controller.reponse.ApiResponse;
import vn.id.hph.kitecine.facade.ReportFacade;
import vn.id.hph.kitecine.facade.dto.DashboardQuickStatsDto;
import vn.id.hph.kitecine.service.model.ChartColumnDto;

@RestController
@RequestMapping("/management/report")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReportController {
    ReportFacade reportFacade;

    @GetMapping("/quick-stats")
    ApiResponse<DashboardQuickStatsDto> getDashboardQuickStats() {
        return ApiResponse.<DashboardQuickStatsDto>builder()
                .result(reportFacade.getDashboardQuickStats())
                .build();
    }

    @GetMapping("/revenue")
    ApiResponse<List<ChartColumnDto>> getRevenueReport(@RequestBody DashboardReportParam param) {
        return ApiResponse.<List<ChartColumnDto>>builder()
                .result(reportFacade.getReport(param))
                .build();
    }
}
