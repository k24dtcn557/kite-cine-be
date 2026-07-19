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
import vn.id.hph.kitecine.controller.param.ShowTimeParam;
import vn.id.hph.kitecine.controller.param.ShowTimeSearchParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.entity.Auditorium;
import vn.id.hph.kitecine.entity.PriceModel;
import vn.id.hph.kitecine.entity.ShowTime;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;
import vn.id.hph.kitecine.mapper.ShowTimeMapper;
import vn.id.hph.kitecine.repository.ShowTimeRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShowTimeService {
    ShowTimeRepository showTimeRepository;
    ShowTimeMapper showTimeMapper;

    public ShowTime create(Auditorium auditorium, PriceModel priceModel, ShowTimeParam param) {
        var entity = showTimeMapper.toShowTime(param);
        entity.setAuditorium(auditorium);
        entity.setPriceModel(priceModel);
        entity.setMovieId(param.movieId());
        return showTimeRepository.save(entity);
    }

    public ShowTime update(Long id, Auditorium auditorium, PriceModel priceModel, ShowTimeParam param) {
        var entity = showTimeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SHOW_TIME_NOT_FOUND));
        showTimeMapper.update(entity, param);
        entity.setAuditorium(auditorium);
        entity.setPriceModel(priceModel);
        entity.setMovieId(param.movieId());
        return showTimeRepository.save(entity);
    }

    public PageResponse<ShowTime> search(ShowTimeSearchParam param) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        PageRequest pageRequest = PageRequest.of(param.getPage(), param.getSize(), sort);

        Specification<ShowTime> query = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(param.getMovieId())) {
                predicates.add(criteriaBuilder.equal(root.get("movieId"), param.getMovieId()));
            }

            if (Objects.nonNull(param.getAuditoriumId())) {
                predicates.add(criteriaBuilder.equal(root.get("auditorium").get("id"), param.getAuditoriumId()));
            }

            if (Objects.nonNull(param.getDate())) {
                predicates.add(criteriaBuilder.equal(root.get("date"), param.getDate()));
            }

            if (StringUtils.hasText(param.getKeyword())) {
                // no free-text fields; skip or match nothing
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<ShowTime> page = showTimeRepository.findAll(query, pageRequest);
        return PageResponse.<ShowTime>builder()
                .totalPages(page.getTotalPages())
                .pageNumber(page.getNumber())
                .totalElements(page.getTotalElements())
                .pageSize(page.getSize())
                .data(page.getContent())
                .build();
    }

    public List<ShowTime> getAll() {
        return showTimeRepository.findAllByOrderByIdDesc();
    }

    public ShowTime get(Long id) {
        return showTimeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SHOW_TIME_NOT_FOUND));
    }

    public void delete(Long id) {
        var entity = showTimeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SHOW_TIME_NOT_FOUND));
        showTimeRepository.delete(entity);
    }

    public List<ShowTime> getByAuditoriumId(Long auditoriumId) {
        return showTimeRepository.findByAuditorium_Id(auditoriumId);
    }
}
