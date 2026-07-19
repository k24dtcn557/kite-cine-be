package vn.id.hph.kitecine.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.id.hph.kitecine.controller.param.MovieParam;
import vn.id.hph.kitecine.controller.param.MovieSearchParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.entity.Movie;
import vn.id.hph.kitecine.enums.MovieStatus;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;
import vn.id.hph.kitecine.mapper.MovieMapper;
import vn.id.hph.kitecine.repository.MovieRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieService {
    MovieRepository movieRepository;
    MovieMapper movieMapper;

    public Movie create(MovieParam param) {
        var movie = movieMapper.toMovie(param);

        movie.setStatus(MovieStatus.DRAFT.name());

        return movieRepository.save(movie);
    }

    public Movie update(Long id, MovieParam param) {
        var movie = movieRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_FOUND));

        movieMapper.update(movie, param);

        return movieRepository.save(movie);
    }

    public PageResponse<Movie> search(MovieSearchParam param) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        PageRequest pageRequest = PageRequest.of(param.getPage(), param.getSize(), sort);

        Specification<Movie> query = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(param.getHighlighted())) {
                predicates.add(criteriaBuilder.equal(root.get("highlighted"), param.getHighlighted()));
            }

            if (StringUtils.hasText(param.getGenre())) {
                Predicate searchPre = criteriaBuilder.or(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("genre")),
                        "%" + param.getKeyword().toLowerCase() + "%"));
                predicates.add(searchPre);
            }

            if (StringUtils.hasText(param.getKeyword())) {
                Predicate searchPre = criteriaBuilder.or(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + param.getKeyword().toLowerCase() + "%"));
                predicates.add(searchPre);
            }

            if (Objects.nonNull(param.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"), param.getStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<Movie> moviePage = movieRepository.findAll(query, pageRequest);
        return PageResponse.<Movie>builder()
                .totalPages(moviePage.getTotalPages())
                .pageNumber(moviePage.getNumber())
                .totalElements(moviePage.getTotalElements())
                .pageSize(moviePage.getSize())
                .data(moviePage.getContent())
                .build();
    }

    public List<Movie> getAll() {
        return movieRepository.findAllByOrderByIdDesc();
    }

    public Movie get(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_FOUND));
    }

    public void delete(Long id) {
        //        var movie = movieRepository.findById(id).orElseThrow(() -> new
        // AppException(ErrorCode.MOVIE_NOT_FOUND));
        //        movie.setStatus(MovieStatus.ARCHIVED.name());
        //        movieRepository.deleteById(movie);
    }

    public void activate(Long id) {
        var movie = movieRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_FOUND));
        movie.setStatus(MovieStatus.NOW_SHOWING.name());
        movieRepository.save(movie);
    }

    public void deactivate(Long id) {
        var movie = movieRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_FOUND));
        movie.setStatus(MovieStatus.COMING_SOON.name());
        movieRepository.save(movie);
    }

    public List<Movie> getHighLightedMovies() {
        return movieRepository.findAllByHighlightedAndStatus(true, MovieStatus.NOW_SHOWING.name());
    }

    public List<Movie> getNowShowingMovies() {
        return movieRepository.findAllByStatus(MovieStatus.NOW_SHOWING.name());
    }

    public List<Movie> getComingSoonMovies() {
        return movieRepository.findAllByStatus(MovieStatus.COMING_SOON.name());
    }
}
