package vn.id.hph.kitecine.facade.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

public record ShowTimeDetailDto(
        long id,
        LocalDate date,
        LocalTime startTime,
        MovieDto movie,
        AuditoriumDto auditorium,
        Instant createdAt,
        Instant updatedAt) {}
