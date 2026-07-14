package vn.id.hph.kitecine.facade.dto;

import java.time.Instant;

import vn.id.hph.kitecine.enums.SeatType;

public record SeatDto(
        long id, String rowLetter, int seatNumber, SeatType seatType, Instant createdAt, Instant updatedAt) {}
