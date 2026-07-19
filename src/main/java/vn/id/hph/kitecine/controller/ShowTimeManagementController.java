package vn.id.hph.kitecine.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.id.hph.kitecine.controller.param.ShowTimeParam;
import vn.id.hph.kitecine.controller.param.ShowTimeSearchParam;
import vn.id.hph.kitecine.controller.reponse.ApiResponse;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.facade.ShowTimeFacade;
import vn.id.hph.kitecine.facade.dto.CinemaShowTimeDto;
import vn.id.hph.kitecine.facade.dto.ShowTimeDto;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShowTimeManagementController {
    ShowTimeFacade showTimeFacade;

    @PostMapping("/show-times")
    public ApiResponse<ShowTimeDto> createShowTime(@RequestBody ShowTimeParam param) {
        return ApiResponse.<ShowTimeDto>builder()
                .result(showTimeFacade.createShowTime(param))
                .build();
    }

    @GetMapping("/show-times")
    public ApiResponse<List<CinemaShowTimeDto>> getShowTimes(@RequestParam Long movieId, @RequestParam LocalDate date) {
        return ApiResponse.<List<CinemaShowTimeDto>>builder()
                .result(showTimeFacade.getShowTimes(movieId, date))
                .build();
    }

    @GetMapping("/auditoriums/{auditoriumId}/show-times")
    public ApiResponse<List<ShowTimeDto>> getShowTimesByAuditorium(
            @PathVariable Long auditoriumId, @RequestParam(required = false) LocalDate date) {
        return ApiResponse.<List<ShowTimeDto>>builder()
                .result(showTimeFacade.getShowTimesByAuditorium(auditoriumId, date))
                .build();
    }

    @PostMapping("/show-times/search")
    public ApiResponse<PageResponse<ShowTimeDto>> searchShowTimes(@RequestBody ShowTimeSearchParam param) {
        return ApiResponse.<PageResponse<ShowTimeDto>>builder()
                .result(showTimeFacade.searchShowTimes(param))
                .build();
    }

    @GetMapping("/show-times/{id}")
    public ApiResponse<ShowTimeDto> getShowTime(@PathVariable Long id) {
        return ApiResponse.<ShowTimeDto>builder()
                .result(showTimeFacade.getShowTime(id))
                .build();
    }

    @DeleteMapping("/show-times/{id}")
    public ApiResponse<Void> deleteShowTime(@PathVariable Long id) {
        showTimeFacade.deleteShowTime(id);
        return ApiResponse.<Void>builder().build();
    }

    @PutMapping("/show-times/{id}")
    public ApiResponse<ShowTimeDto> updateShowTime(@PathVariable Long id, @RequestBody ShowTimeParam param) {
        return ApiResponse.<ShowTimeDto>builder()
                .result(showTimeFacade.updateShowTime(id, param))
                .build();
    }
}
