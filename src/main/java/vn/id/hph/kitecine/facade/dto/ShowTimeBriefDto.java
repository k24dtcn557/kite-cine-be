package vn.id.hph.kitecine.facade.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ShowTimeBriefDto(long id, LocalDate date, LocalTime startTime, LocalTime endTime) {}
