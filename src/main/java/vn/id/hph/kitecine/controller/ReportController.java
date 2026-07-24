package vn.id.hph.kitecine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.reponse.ApiResponse;
import vn.id.hph.kitecine.facade.ReportFacade;
import vn.id.hph.kitecine.facade.dto.DashboardQuickStatsDto;

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
}
