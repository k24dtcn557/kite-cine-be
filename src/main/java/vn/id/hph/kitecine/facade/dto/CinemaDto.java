package vn.id.hph.kitecine.facade.dto;

import java.time.Instant;

public record CinemaDto(long id, String name, String address, Instant createdAt, Instant updatedAt, String status) {}
