package vn.id.hph.kitecine.mapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import vn.id.hph.kitecine.controller.param.MovieParam;
import vn.id.hph.kitecine.entity.Movie;
import vn.id.hph.kitecine.facade.dto.MovieDto;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    @Mapping(source = "genres", target = "genre")
    Movie toMovie(MovieParam param);

    @Mapping(source = "genre", target = "genres")
    MovieDto toMovieDto(Movie entity);

    @Mapping(target = "genre", source = "genres")
    void update(@MappingTarget Movie entity, MovieParam param);

    List<MovieDto> toMovieDtoList(List<Movie> entities);

    // helper methods used by MapStruct to convert between List<String> and stored String
    default String genresToString(List<String> genres) {
        if (genres == null || genres.isEmpty()) return null;
        return String.join("/", genres.stream().map(String::trim).collect(Collectors.toList()));
    }

    default List<String> stringToGenres(String genre) {
        if (genre == null || genre.isBlank()) return Collections.emptyList();
        return Arrays.stream(genre.split("/"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
