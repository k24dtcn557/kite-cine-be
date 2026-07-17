package vn.id.hph.kitecine.controller.param;

import vn.id.hph.kitecine.enums.AuditoriumType;

public record AuditoriumParam(long cinemaId, String name, AuditoriumType type) {}
