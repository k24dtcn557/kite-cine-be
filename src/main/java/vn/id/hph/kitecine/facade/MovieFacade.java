package vn.id.hph.kitecine.facade;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.param.MovieParam;
import vn.id.hph.kitecine.controller.param.MovieSearchParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.facade.dto.CrewMemberDto;
import vn.id.hph.kitecine.facade.dto.MovieDto;
import vn.id.hph.kitecine.mapper.MovieMapper;
import vn.id.hph.kitecine.service.MovieService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MovieFacade {
    MovieService movieService;
    MovieMapper movieMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public MovieDto createMovie(MovieParam param) {
        var movie = movieService.create(param);
        return movieMapper.toMovieDto(movie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<MovieDto> getMovies() {
        var movies = movieService.getAll();
        return movieMapper.toMovieDtoList(movies);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<MovieDto> searchMovies(MovieSearchParam param) {
        var moviePageResponse = movieService.search(param);
        var movieDtos = movieMapper.toMovieDtoList(moviePageResponse.getData());

        return PageResponse.<MovieDto>builder()
                .data(movieDtos)
                .pageNumber(moviePageResponse.getPageNumber())
                .pageSize(moviePageResponse.getPageSize())
                .totalElements(moviePageResponse.getTotalElements())
                .totalPages(moviePageResponse.getTotalPages())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public MovieDto updateMovie(Long movieId, MovieParam param) {
        var movie = movieService.update(movieId, param);
        return movieMapper.toMovieDto(movie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMovie(Long movieId) {
        movieService.delete(movieId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public MovieDto getMovie(Long movieId) {
        var movie = movieService.get(movieId);
        return movieMapper.toMovieDto(movie);
    }

    public MovieDto viewMovie(Long movieId) {
        var movie = movieService.get(movieId);
        return movieMapper.toMovieDto(movie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void activateMovie(Long movieId) {
        movieService.activate(movieId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deactivateMovie(Long movieId) {
        movieService.deactivate(movieId);
    }

    public List<MovieDto> getHighLightedMovies() {
        var movies = movieService.getHighLightedMovies();
        return movieMapper.toMovieDtoList(movies);
    }

    public List<MovieDto> getNowShowingMovies(String genre) {
        var movies = movieService.getNowShowingMovies(genre);
        return movieMapper.toMovieDtoList(movies);
    }

    public List<MovieDto> getComingSoonMovies() {
        var movies = movieService.getComingSoonMovies();
        return movieMapper.toMovieDtoList(movies);
    }

    public List<CrewMemberDto> getCrewMembers(Long id) {
        return null;
    }
}
