package vn.id.hph.kitecine.facade.dto;

import java.time.Instant;

public record CrewMemberDto(
        long id,
        String role,
        long movieId,
        long crewPersonId,
        String name,
        String avatar,
        Instant createdAt,
        Instant updatedAt) {}
