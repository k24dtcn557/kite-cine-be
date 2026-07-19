package vn.id.hph.kitecine.facade.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

public record ShowTimeDto(
        long id,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        long movieId,
        long auditoriumId,
        long priceModelId,
        Instant createdAt,
        Instant updatedAt) {}
