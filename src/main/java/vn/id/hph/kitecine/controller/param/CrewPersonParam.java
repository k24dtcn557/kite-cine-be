package vn.id.hph.kitecine.controller.param;

import java.time.LocalDate;

public record CrewPersonParam(String name, String avatar, String bio, LocalDate dob) {}
