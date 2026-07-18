package vn.id.hph.kitecine.controller.param;

import java.time.LocalDate;
import java.util.List;

public record MovieParam(
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
        String status) {}
