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
import vn.id.hph.kitecine.controller.param.CrewMemberParam;
import vn.id.hph.kitecine.controller.param.CrewMemberSearchParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.entity.CrewMember;
import vn.id.hph.kitecine.entity.CrewPerson;
import vn.id.hph.kitecine.entity.Movie;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;
import vn.id.hph.kitecine.mapper.CrewMemberMapper;
import vn.id.hph.kitecine.repository.CrewMemberRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CrewMemberService {
    CrewMemberRepository crewMemberRepository;
    CrewMemberMapper crewMemberMapper;

    public CrewMember create(Movie movie, CrewPerson crewPerson, CrewMemberParam param) {
        var crewMember = crewMemberMapper.toCrewMember(param);
        crewMember.setMovie(movie);
        crewMember.setCrewPerson(crewPerson);

        return crewMemberRepository.save(crewMember);
    }

    public CrewMember update(Long id, Movie movie, CrewPerson crewPerson, CrewMemberParam param) {
        var crewMember =
                crewMemberRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CREW_MEMBER_NOT_FOUND));

        crewMemberMapper.update(crewMember, param);
        crewMember.setMovie(movie);
        crewMember.setCrewPerson(crewPerson);

        return crewMemberRepository.save(crewMember);
    }

    public PageResponse<CrewMember> search(CrewMemberSearchParam param) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        PageRequest pageRequest = PageRequest.of(param.getPage(), param.getSize(), sort);

        Specification<CrewMember> query = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(param.getKeyword())) {
                Predicate searchPre = criteriaBuilder.or(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("role")),
                        "%" + param.getKeyword().toLowerCase() + "%"));
                predicates.add(searchPre);
            }

            if (Objects.nonNull(param.getMovieId())) {
                predicates.add(criteriaBuilder.equal(root.get("movie").get("id"), param.getMovieId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<CrewMember> page = crewMemberRepository.findAll(query, pageRequest);
        return PageResponse.<CrewMember>builder()
                .totalPages(page.getTotalPages())
                .pageNumber(page.getNumber())
                .totalElements(page.getTotalElements())
                .pageSize(page.getSize())
                .data(page.getContent())
                .build();
    }

    public List<CrewMember> getAll() {
        return crewMemberRepository.findAll();
    }

    public CrewMember get(Long id) {
        return crewMemberRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CREW_MEMBER_NOT_FOUND));
    }

    public void delete(Long id) {
        var crewMember =
                crewMemberRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CREW_MEMBER_NOT_FOUND));

        crewMemberRepository.delete(crewMember);
    }

    public List<CrewMember> getByMovieId(Long movieId) {
        return crewMemberRepository.findByMovieId(movieId);
    }
}
