package vn.id.hph.kitecine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.id.hph.kitecine.controller.reponse.ApiResponse;
import vn.id.hph.kitecine.facade.MovieFacade;
import vn.id.hph.kitecine.facade.dto.MovieDto;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieController {
    MovieFacade movieFacade;

    @GetMapping("/movies/highlighted")
    public ApiResponse<List<MovieDto>> getHighLightedMovies() {
        return ApiResponse.<List<MovieDto>>builder()
                .result(movieFacade.getHighLightedMovies())
                .build();
    }

    @GetMapping("/movies/now-showing")
    public ApiResponse<List<MovieDto>> getNowShowingMovies() {
        return ApiResponse.<List<MovieDto>>builder()
                .result(movieFacade.getNowShowingMovies())
                .build();
    }

    @GetMapping("/movies/coming-soon")
    public ApiResponse<List<MovieDto>> getComingSoonMovies() {
        return ApiResponse.<List<MovieDto>>builder()
                .result(movieFacade.getComingSoonMovies())
                .build();
    }

    @GetMapping("/movies/{id}")
    public ApiResponse<MovieDto> getMovie(@PathVariable Long id) {
        return ApiResponse.<MovieDto>builder().result(movieFacade.getMovie(id)).build();
    }
}
