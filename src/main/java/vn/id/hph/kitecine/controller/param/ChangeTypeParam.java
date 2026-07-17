package vn.id.hph.kitecine.controller.param;

import java.util.List;

import vn.id.hph.kitecine.enums.SeatType;

public record ChangeTypeParam(List<Long> ids, SeatType seatType) {}
