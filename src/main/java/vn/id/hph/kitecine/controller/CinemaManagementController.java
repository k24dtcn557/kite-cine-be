package vn.id.hph.kitecine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.id.hph.kitecine.controller.param.AddRowParam;
import vn.id.hph.kitecine.controller.param.AuditoriumParam;
import vn.id.hph.kitecine.controller.param.AuditoriumSearchParam;
import vn.id.hph.kitecine.controller.param.ChangeTypeParam;
import vn.id.hph.kitecine.controller.param.CinemaParam;
import vn.id.hph.kitecine.controller.param.DeleteSeatParam;
import vn.id.hph.kitecine.controller.param.KeywordStatusSearchParam;
import vn.id.hph.kitecine.controller.param.SeatParam;
import vn.id.hph.kitecine.controller.param.SeatSearchParam;
import vn.id.hph.kitecine.controller.reponse.ApiResponse;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.facade.CinemaFacade;
import vn.id.hph.kitecine.facade.dto.AuditoriumDto;
import vn.id.hph.kitecine.facade.dto.CinemaDto;
import vn.id.hph.kitecine.facade.dto.SeatDto;
import vn.id.hph.kitecine.facade.dto.SeatRowDto;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CinemaManagementController {
    CinemaFacade cinemaFacade;

    @PostMapping("/cinemas")
    public ApiResponse<CinemaDto> createCinema(@RequestBody CinemaParam param) {
        return ApiResponse.<CinemaDto>builder()
                .result(cinemaFacade.createCinema(param))
                .build();
    }

    @GetMapping("/cinemas")
    public ApiResponse<List<CinemaDto>> getCinemas() {
        return ApiResponse.<List<CinemaDto>>builder()
                .result(cinemaFacade.getCinemas())
                .build();
    }

    @PostMapping("/cinemas/search")
    public ApiResponse<PageResponse<CinemaDto>> searchCinemas(@RequestBody KeywordStatusSearchParam param) {
        return ApiResponse.<PageResponse<CinemaDto>>builder()
                .result(cinemaFacade.searchCinemas(param))
                .build();
    }

    @GetMapping("/cinemas/{id}")
    public ApiResponse<CinemaDto> getCinema(@PathVariable Long id) {
        return ApiResponse.<CinemaDto>builder()
                .result(cinemaFacade.getCinema(id))
                .build();
    }

    @DeleteMapping("/cinemas/{id}")
    public ApiResponse<Void> deleteCinema(@PathVariable Long id) {
        cinemaFacade.deleteCinema(id);
        return ApiResponse.<Void>builder().build();
    }

    @PutMapping("/cinemas/{id}")
    public ApiResponse<CinemaDto> updateCinema(@PathVariable Long id, @RequestBody CinemaParam param) {
        return ApiResponse.<CinemaDto>builder()
                .result(cinemaFacade.updateCinema(id, param))
                .build();
    }

    @PostMapping("/cinemas/{id}/activate")
    public ApiResponse<Void> activateCinema(@PathVariable Long id) {
        cinemaFacade.activateCinema(id);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/cinemas/{id}/de-activate")
    public ApiResponse<Void> deactivateCinema(@PathVariable Long id) {
        cinemaFacade.deactivateCinema(id);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/cinemas/auditoriums")
    public ApiResponse<AuditoriumDto> createAuditorium(@RequestBody AuditoriumParam param) {
        return ApiResponse.<AuditoriumDto>builder()
                .result(cinemaFacade.createAuditorium(param))
                .build();
    }

    @PostMapping("/cinemas/auditoriums/search")
    public ApiResponse<PageResponse<AuditoriumDto>> searchAuditoriums(@RequestBody AuditoriumSearchParam param) {
        return ApiResponse.<PageResponse<AuditoriumDto>>builder()
                .result(cinemaFacade.searchAuditoriums(param))
                .build();
    }

    @GetMapping("/cinemas/auditoriums/{id}")
    public ApiResponse<AuditoriumDto> getAuditorium(@PathVariable Long id) {
        return ApiResponse.<AuditoriumDto>builder()
                .result(cinemaFacade.getAuditorium(id))
                .build();
    }

    @PutMapping("/cinemas/auditoriums/{id}")
    public ApiResponse<AuditoriumDto> updateAuditorium(@PathVariable Long id, @RequestBody AuditoriumParam param) {
        return ApiResponse.<AuditoriumDto>builder()
                .result(cinemaFacade.updateAuditorium(id, param))
                .build();
    }

    @DeleteMapping("/cinemas/auditoriums/{id}")
    public ApiResponse<Void> deleteAuditorium(@PathVariable Long id) {
        cinemaFacade.deleteAuditorium(id);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/cinemas/auditoriums/{id}/activate")
    public ApiResponse<Void> activateAuditorium(@PathVariable Long id) {
        cinemaFacade.activateAuditorium(id);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/cinemas/auditoriums/{id}/de-activate")
    public ApiResponse<Void> deActivateAuditorium(@PathVariable Long id) {
        cinemaFacade.deactivateAuditorium(id);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/cinemas/auditoriums/seats")
    public ApiResponse<SeatDto> createSeat(@RequestBody SeatParam param) {
        return ApiResponse.<SeatDto>builder()
                .result(cinemaFacade.createSeat(param))
                .build();
    }

    @PostMapping("/cinemas/auditoriums/seats/add-row")
    public ApiResponse<List<SeatDto>> addRow(@RequestBody AddRowParam param) {
        return ApiResponse.<List<SeatDto>>builder()
                .result(cinemaFacade.addRow(param))
                .build();
    }

    @PostMapping("/cinemas/auditoriums/seats/search")
    public ApiResponse<PageResponse<SeatDto>> searchSeats(@RequestBody SeatSearchParam param) {
        return ApiResponse.<PageResponse<SeatDto>>builder()
                .result(cinemaFacade.searchSeats(param))
                .build();
    }

    @GetMapping("/cinemas/auditoriums/{id}/seats")
    public ApiResponse<List<SeatRowDto>> getAuditoriumSeats(@PathVariable Long id) {
        return ApiResponse.<List<SeatRowDto>>builder()
                .result(cinemaFacade.getAuditoriumSeats(id))
                .build();
    }

    @GetMapping("/cinemas/auditoriums/seats/{id}")
    public ApiResponse<SeatDto> getSeat(@PathVariable Long id) {
        return ApiResponse.<SeatDto>builder().result(cinemaFacade.getSeat(id)).build();
    }

    @PutMapping("/cinemas/auditoriums/seats/{id}")
    public ApiResponse<SeatDto> updateSeat(@PathVariable Long id, @RequestBody SeatParam param) {
        return ApiResponse.<SeatDto>builder()
                .result(cinemaFacade.updateSeat(id, param))
                .build();
    }

    @DeleteMapping("/cinemas/auditoriums/seats/{id}")
    public ApiResponse<Void> deleteSeat(@PathVariable Long id) {
        cinemaFacade.deleteSeat(id);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/cinemas/auditoriums/seats/delete")
    public ApiResponse<Void> deleteSeats(@RequestBody DeleteSeatParam param) {
        cinemaFacade.deleteSeats(param.ids());
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/cinemas/auditoriums/seats/change-type")
    public ApiResponse<Void> changeSeatType(@RequestBody ChangeTypeParam param) {
        cinemaFacade.changeSeatType(param);
        return ApiResponse.<Void>builder().build();
    }
}
