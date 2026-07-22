package vn.id.hph.kitecine.facade.dto;

import java.time.Instant;

public record AuditoriumDetailDto(
        long id, String name, String type, CinemaDto cinema, Instant createdAt, Instant updatedAt, String status) {}
