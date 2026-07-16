package vn.id.hph.kitecine.controller.param;

import vn.id.hph.kitecine.enums.SeatType;

public record AddRowParam(long auditoriumId, String rowLetter, int numberOfSeats, SeatType seatType) {}
