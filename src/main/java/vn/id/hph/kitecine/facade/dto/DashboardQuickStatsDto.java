package vn.id.hph.kitecine.facade.dto;

import java.math.BigDecimal;

public record DashboardQuickStatsDto(
        BigDecimal revenue, int totalCinemas, int totalAuditoriums, int totalSoldSeats, Double fillRate) {}
