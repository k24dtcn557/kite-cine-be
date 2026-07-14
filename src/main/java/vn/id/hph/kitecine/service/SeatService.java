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

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.id.hph.kitecine.controller.param.SeatParam;
import vn.id.hph.kitecine.controller.param.SeatSearchParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.entity.Auditorium;
import vn.id.hph.kitecine.entity.Seat;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;
import vn.id.hph.kitecine.mapper.SeatMapper;
import vn.id.hph.kitecine.repository.SeatRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SeatService {
    SeatRepository seatRepository;
    SeatMapper seatMapper;

    public Seat create(Auditorium auditorium, SeatParam param) {
        var seat = seatMapper.toSeat(param);
        seat.setAuditorium(auditorium);

        return seatRepository.save(seat);
    }

    public Seat update(Long id, Auditorium auditorium, SeatParam param) {
        var seat = seatRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SEAT_NOT_FOUND));

        seatMapper.update(seat, param);
        seat.setAuditorium(auditorium);

        return seatRepository.save(seat);
    }

    public PageResponse<Seat> search(SeatSearchParam param) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        PageRequest pageRequest = PageRequest.of(param.getPage(), param.getSize(), sort);

        // Search by criteria
        Specification<Seat> query = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(param.getAuditoriumId())) {
                predicates.add(criteriaBuilder.equal(root.get("auditorium").get("id"), param.getAuditoriumId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<Seat> seatPage = seatRepository.findAll(query, pageRequest);
        return PageResponse.<Seat>builder()
                .totalPages(seatPage.getTotalPages())
                .pageNumber(seatPage.getNumber())
                .totalElements(seatPage.getTotalElements())
                .pageSize(seatPage.getSize())
                .data(seatPage.getContent())
                .build();
    }

    public List<Seat> getAll() {
        return seatRepository.findAll();
    }

    public Seat get(Long id) {
        return seatRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SEAT_NOT_FOUND));
    }

    public void delete(Long id) {
        var seat = seatRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SEAT_NOT_FOUND));
        seatRepository.delete(seat);
    }
}
