package vn.id.hph.kitecine.controller.param;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.id.hph.kitecine.enums.ReportType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @JsonIgnore)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardReportParam {
    ReportType type;
}
