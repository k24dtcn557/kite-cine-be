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
import vn.id.hph.kitecine.controller.param.CinemaParam;
import vn.id.hph.kitecine.controller.param.KeywordStatusSearchParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.entity.Cinema;
import vn.id.hph.kitecine.enums.CinemaStatus;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;
import vn.id.hph.kitecine.mapper.CinemaMapper;
import vn.id.hph.kitecine.repository.CinemaRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CinemaService {
    CinemaRepository cinemaRepository;
    CinemaMapper cinemaMapper;

    public Cinema create(CinemaParam param) {
        var cinema = cinemaMapper.toCinema(param);
        cinema.setStatus(CinemaStatus.ACTIVE.name());

        return cinemaRepository.save(cinema);
    }

    public Cinema update(Long id, CinemaParam param) {
        var cinema = cinemaRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_FOUND));

        cinemaMapper.update(cinema, param);

        return cinemaRepository.save(cinema);
    }

    public PageResponse<Cinema> search(KeywordStatusSearchParam param) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        PageRequest pageRequest = PageRequest.of(param.getPage(), param.getSize(), sort);

        // Search by criteria
        Specification<Cinema> query = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(param.getKeyword())) {
                Predicate searchPre = criteriaBuilder.or(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + param.getKeyword().toLowerCase() + "%"));
                predicates.add(searchPre);
            }

            if (Objects.nonNull(param.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"), param.getStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<Cinema> cinemaPage = cinemaRepository.findAll(query, pageRequest);
        return PageResponse.<Cinema>builder()
                .totalPages(cinemaPage.getTotalPages())
                .pageNumber(cinemaPage.getNumber())
                .totalElements(cinemaPage.getTotalElements())
                .pageSize(cinemaPage.getSize())
                .data(cinemaPage.getContent())
                .build();
    }

    public List<Cinema> getAll() {
        return cinemaRepository.findAllByStatusOrderByIdDesc(CinemaStatus.ACTIVE.name());
    }

    public Cinema get(Long id) {
        return cinemaRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_FOUND));
    }

    public void delete(Long id) {
        var cinema = cinemaRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_FOUND));
        cinema.setStatus(CinemaStatus.DELETED.name());
        cinemaRepository.save(cinema);
    }

    public void activate(Long id) {
        var cinema = cinemaRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_FOUND));
        cinema.setStatus(CinemaStatus.ACTIVE.name());
        cinemaRepository.save(cinema);
    }

    public void deactivate(Long id) {
        var cinema = cinemaRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_FOUND));
        cinema.setStatus(CinemaStatus.INACTIVE.name());
        cinemaRepository.save(cinema);
    }

    public int getNumberOfCinemas() {
        return (int) cinemaRepository.countAllByStatus(CinemaStatus.ACTIVE.name());
    }
}
