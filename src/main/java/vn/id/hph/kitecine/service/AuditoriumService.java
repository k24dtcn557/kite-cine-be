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
import vn.id.hph.kitecine.controller.param.AuditoriumParam;
import vn.id.hph.kitecine.controller.param.AuditoriumSearchParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.entity.Auditorium;
import vn.id.hph.kitecine.entity.Cinema;
import vn.id.hph.kitecine.enums.AuditoriumStatus;
import vn.id.hph.kitecine.enums.CinemaStatus;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;
import vn.id.hph.kitecine.mapper.AuditoriumMapper;
import vn.id.hph.kitecine.repository.AuditoriumRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuditoriumService {
    AuditoriumRepository auditoriumRepository;
    AuditoriumMapper auditoriumMapper;

    public Auditorium create(Cinema cinema, AuditoriumParam param) {
        var auditorium = auditoriumMapper.toAuditorium(param);
        auditorium.setCinema(cinema);
        auditorium.setStatus(AuditoriumStatus.ACTIVE.name());

        return auditoriumRepository.save(auditorium);
    }

    public Auditorium update(Long id, Cinema cinema, AuditoriumParam param) {
        var auditorium =
                auditoriumRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AUDITORIUM_NOT_FOUND));

        auditoriumMapper.update(auditorium, param);
        auditorium.setCinema(cinema);

        return auditoriumRepository.save(auditorium);
    }

    public PageResponse<Auditorium> search(AuditoriumSearchParam param) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        PageRequest pageRequest = PageRequest.of(param.getPage(), param.getSize(), sort);

        // Search by criteria
        Specification<Auditorium> query = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(param.getCinemaId())) {
                predicates.add(criteriaBuilder.equal(root.get("cinema").get("id"), param.getCinemaId()));
            }

            if (StringUtils.hasText(param.getKeyword())) {
                Predicate searchPre = criteriaBuilder.or(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + param.getKeyword().toLowerCase() + "%"));
                predicates.add(searchPre);
            }

            if (StringUtils.hasText(param.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"), param.getStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<Auditorium> auditoriumPage = auditoriumRepository.findAll(query, pageRequest);
        return PageResponse.<Auditorium>builder()
                .totalPages(auditoriumPage.getTotalPages())
                .pageNumber(auditoriumPage.getNumber())
                .totalElements(auditoriumPage.getTotalElements())
                .pageSize(auditoriumPage.getSize())
                .data(auditoriumPage.getContent())
                .build();
    }

    public List<Auditorium> getAll() {
        return auditoriumRepository.findAll();
    }

    public Auditorium get(Long id) {
        return auditoriumRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AUDITORIUM_NOT_FOUND));
    }

    public void delete(Long id) {
        var auditorium =
                auditoriumRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AUDITORIUM_NOT_FOUND));
        auditorium.setStatus(AuditoriumStatus.DELETED.name());
        auditoriumRepository.save(auditorium);
    }

    public void activate(Long id) {
        var auditorium =
                auditoriumRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AUDITORIUM_NOT_FOUND));
        auditorium.setStatus(AuditoriumStatus.ACTIVE.name());
        auditoriumRepository.save(auditorium);
    }

    public void deactivate(Long id) {
        var auditorium =
                auditoriumRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AUDITORIUM_NOT_FOUND));
        auditorium.setStatus(AuditoriumStatus.INACTIVE.name());
        auditoriumRepository.save(auditorium);
    }

    public int getNumberOfAuditoriums(Long id) {
        return auditoriumRepository.countByCinema_Id(id);
    }

    public int getNumberOfAuditoriums() {
        return auditoriumRepository.countAllByStatusAndCinema_Status(
                AuditoriumStatus.ACTIVE.name(), CinemaStatus.ACTIVE.name());
    }
}
