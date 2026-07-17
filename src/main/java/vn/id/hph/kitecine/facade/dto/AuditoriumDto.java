package vn.id.hph.kitecine.facade.dto;

import java.time.Instant;

public record AuditoriumDto(long id, String name, String type, Instant createdAt, Instant updatedAt, String status) {}
