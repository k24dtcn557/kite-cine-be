package vn.id.hph.kitecine.facade.dto;

import java.time.Instant;
import java.time.LocalDate;

public record CrewPersonDto(
        long id, String name, String avatar, String bio, LocalDate dob, Instant createdAt, Instant updatedAt) {}
