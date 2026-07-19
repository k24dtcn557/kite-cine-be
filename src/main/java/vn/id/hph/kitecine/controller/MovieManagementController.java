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
import vn.id.hph.kitecine.controller.param.KeywordStatusSearchParam;
import vn.id.hph.kitecine.controller.param.MovieParam;
import vn.id.hph.kitecine.controller.param.MovieSearchParam;
import vn.id.hph.kitecine.controller.reponse.ApiResponse;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.facade.MovieFacade;
import vn.id.hph.kitecine.facade.dto.MovieDto;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieManagementController {
    MovieFacade movieFacade;

    @PostMapping("/movies")
    public ApiResponse<MovieDto> createMovie(@RequestBody MovieParam param) {
        return ApiResponse.<MovieDto>builder()
                .result(movieFacade.createMovie(param))
                .build();
    }

    @GetMapping("/movies")
    public ApiResponse<List<MovieDto>> getMovies() {
        return ApiResponse.<List<MovieDto>>builder()
                .result(movieFacade.getMovies())
                .build();
    }

    @PostMapping("/movies/search")
    public ApiResponse<PageResponse<MovieDto>> searchMovies(@RequestBody MovieSearchParam param) {
        return ApiResponse.<PageResponse<MovieDto>>builder()
                .result(movieFacade.searchMovies(param))
                .build();
    }

    @GetMapping("/movies/{id}")
    public ApiResponse<MovieDto> getMovie(@PathVariable Long id) {
        return ApiResponse.<MovieDto>builder().result(movieFacade.getMovie(id)).build();
    }

    @DeleteMapping("/movies/{id}")
    public ApiResponse<Void> deleteMovie(@PathVariable Long id) {
        movieFacade.deleteMovie(id);
        return ApiResponse.<Void>builder().build();
    }

    @PutMapping("/movies/{id}")
    public ApiResponse<MovieDto> updateMovie(@PathVariable Long id, @RequestBody MovieParam param) {
        return ApiResponse.<MovieDto>builder()
                .result(movieFacade.updateMovie(id, param))
                .build();
    }

    @PostMapping("/movies/{id}/activate")
    public ApiResponse<Void> activateMovie(@PathVariable Long id) {
        movieFacade.activateMovie(id);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/movies/{id}/de-activate")
    public ApiResponse<Void> deactivateMovie(@PathVariable Long id) {
        movieFacade.deactivateMovie(id);
        return ApiResponse.<Void>builder().build();
    }
}
