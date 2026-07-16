package vn.id.hph.kitecine.facade.dto;

import java.util.List;

public record SeatRowDto(String rowLetter, List<SeatDto> seats) {}
