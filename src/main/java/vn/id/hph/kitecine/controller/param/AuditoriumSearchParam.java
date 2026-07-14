package vn.id.hph.kitecine.controller.param;

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
public class AuditoriumSearchParam extends PageSizeSearchParam {
    Long cinemaId;
    String keyword;
    String status;
}
