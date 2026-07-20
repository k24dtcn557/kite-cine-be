package vn.id.hph.kitecine.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.id.hph.kitecine.controller.param.TicketParam;
import vn.id.hph.kitecine.controller.reponse.ApiResponse;
import vn.id.hph.kitecine.facade.BookingFacade;
import vn.id.hph.kitecine.facade.CinemaFacade;
import vn.id.hph.kitecine.facade.CrewMemberFacade;
import vn.id.hph.kitecine.facade.MovieFacade;
import vn.id.hph.kitecine.facade.ShowTimeFacade;
import vn.id.hph.kitecine.facade.dto.CinemaShowTimeDto;
import vn.id.hph.kitecine.facade.dto.CrewMemberDto;
import vn.id.hph.kitecine.facade.dto.MovieDto;
import vn.id.hph.kitecine.facade.dto.SeatRowDto;
import vn.id.hph.kitecine.facade.dto.TicketDto;
import vn.id.hph.kitecine.mapper.TicketMapper;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieBookingController {
    ShowTimeFacade showTimeFacade;
    CinemaFacade cinemaFacade;
    MovieFacade movieFacade;
    CrewMemberFacade crewMemberFacade;
    BookingFacade bookingFacade;

    TicketMapper ticketMapper;

    @GetMapping("/movies/highlighted")
    public ApiResponse<List<MovieDto>> getHighLightedMovies() {
        return ApiResponse.<List<MovieDto>>builder()
                .result(movieFacade.getHighLightedMovies())
                .build();
    }

    @GetMapping("/movies/now-showing")
    public ApiResponse<List<MovieDto>> getNowShowingMovies(@RequestParam(required = false) String genre) {
        return ApiResponse.<List<MovieDto>>builder()
                .result(movieFacade.getNowShowingMovies(genre))
                .build();
    }

    @GetMapping("/movies/coming-soon")
    public ApiResponse<List<MovieDto>> getComingSoonMovies() {
        return ApiResponse.<List<MovieDto>>builder()
                .result(movieFacade.getComingSoonMovies())
                .build();
    }

    @GetMapping("/movies/{id}/detail")
    public ApiResponse<MovieDto> getMovie(@PathVariable Long id) {
        return ApiResponse.<MovieDto>builder().result(movieFacade.getMovie(id)).build();
    }

    @GetMapping("/movies/{id}/show-times")
    public ApiResponse<List<CinemaShowTimeDto>> getMovieShowTime(@PathVariable Long id, LocalDate date) {
        return ApiResponse.<List<CinemaShowTimeDto>>builder()
                .result(showTimeFacade.getMovieShowTimes(id, date))
                .build();
    }

    @GetMapping("/movies/{id}/crew-members")
    public ApiResponse<List<CrewMemberDto>> getCrewMembers(@PathVariable Long id) {
        return ApiResponse.<List<CrewMemberDto>>builder()
                .result(crewMemberFacade.getMovieCrewMembers(id))
                .build();
    }

    @GetMapping("/auditoriums/{id}/seats")
    public ApiResponse<List<SeatRowDto>> getSeats(@PathVariable Long id) {
        return ApiResponse.<List<SeatRowDto>>builder()
                .result(cinemaFacade.getAuditoriumSeats(id))
                .build();
    }

    @PostMapping("/tickets/reserve")
    public ApiResponse<TicketDto> reserveTicket(@RequestBody TicketParam param) {
        return ApiResponse.<TicketDto>builder()
                .result(bookingFacade.reserve(param))
                .build();
    }

    @DeleteMapping("/tickets/{id}")
    public ApiResponse<Void> deleteTicket(@PathVariable Long id) {
        bookingFacade.delete(id);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/tickets/{id}")
    public ApiResponse<TicketDto> getTicket(@PathVariable Long id) {
        return ApiResponse.<TicketDto>builder()
                .result(bookingFacade.getTicket(id))
                .build();
    }

    @GetMapping("/tickets/showtime/{showtimeId}")
    public ApiResponse<List<TicketDto>> getTicketsByShowtime(@PathVariable Long showtimeId) {
        return ApiResponse.<List<TicketDto>>builder()
                .result(bookingFacade.getByShowTime(showtimeId))
                .build();
    }

    @GetMapping("/tickets/my-tickets")
    public ApiResponse<List<TicketDto>> getMyTickets() {
        return ApiResponse.<List<TicketDto>>builder()
                .result(bookingFacade.getMyTickets())
                .build();
    }

    @GetMapping("/tickets/showtime/{showtimeId}/my-holdings")
    public ApiResponse<List<TicketDto>> getMyHoldings(@PathVariable Long showtimeId) {
        return ApiResponse.<List<TicketDto>>builder()
                .result(bookingFacade.getMyHoldings(showtimeId))
                .build();
    }
}
