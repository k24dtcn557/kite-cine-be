package vn.id.hph.kitecine.facade.dto;

import java.time.Instant;

public record AuditoriumWithCinemaDto(
        long id, String name, CinemaDto cinema, Instant createdAt, Instant updatedAt, String status) {}
