package vn.id.hph.kitecine.controller.param;

import java.time.LocalDate;
import java.time.LocalTime;

public record ShowTimeParam(LocalDate date, LocalTime startTime, Long movieId, Long auditoriumId, Long priceModelId) {}
