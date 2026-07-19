package vn.id.hph.kitecine.controller.param;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowTimeSearchParam extends PageSizeSearchParam {
    Long movieId;
    Long auditoriumId;
    LocalDate date;
    String keyword;
}
