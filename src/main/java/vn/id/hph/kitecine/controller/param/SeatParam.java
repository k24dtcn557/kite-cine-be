package vn.id.hph.kitecine.controller.param;

import vn.id.hph.kitecine.enums.SeatType;

public record SeatParam(long auditoriumId, String rowLetter, int seatNumber, SeatType seatType) {}
