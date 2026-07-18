package vn.id.hph.kitecine.facade.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record MovieDto(
        long id,
        String title,
        String tagline,
        String description,
        List<String> genres,
        Integer runtime,
        String poster,
        String background,
        String video,
        LocalDate releaseDate,
        boolean highlighted,
        Instant createdAt,
        Instant updatedAt,
        String status) {}
