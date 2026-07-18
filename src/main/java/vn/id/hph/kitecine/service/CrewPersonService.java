package vn.id.hph.kitecine.service;

import java.util.ArrayList;
import java.util.List;

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
import vn.id.hph.kitecine.controller.param.CrewPersonParam;
import vn.id.hph.kitecine.controller.param.KeywordStatusSearchParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.entity.CrewPerson;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;
import vn.id.hph.kitecine.mapper.CrewPersonMapper;
import vn.id.hph.kitecine.repository.CrewPersonRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CrewPersonService {
    CrewPersonRepository crewPersonRepository;
    CrewPersonMapper crewPersonMapper;

    public CrewPerson create(CrewPersonParam param) {
        var crewPerson = crewPersonMapper.toCrewPerson(param);
        return crewPersonRepository.save(crewPerson);
    }

    public CrewPerson update(Long id, CrewPersonParam param) {
        var crewPerson =
                crewPersonRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CREW_PERSON_NOT_FOUND));

        crewPersonMapper.update(crewPerson, param);
        return crewPersonRepository.save(crewPerson);
    }

    public PageResponse<CrewPerson> search(KeywordStatusSearchParam param) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        PageRequest pageRequest = PageRequest.of(param.getPage(), param.getSize(), sort);

        Specification<CrewPerson> query = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(param.getKeyword())) {
                Predicate searchPre = criteriaBuilder.or(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + param.getKeyword().toLowerCase() + "%"));
                predicates.add(searchPre);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<CrewPerson> page = crewPersonRepository.findAll(query, pageRequest);
        return PageResponse.<CrewPerson>builder()
                .totalPages(page.getTotalPages())
                .pageNumber(page.getNumber())
                .totalElements(page.getTotalElements())
                .pageSize(page.getSize())
                .data(page.getContent())
                .build();
    }

    public List<CrewPerson> getAll() {
        return crewPersonRepository.findAll();
    }

    public CrewPerson get(Long id) {
        return crewPersonRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CREW_PERSON_NOT_FOUND));
    }

    public void delete(Long id) {
        var crewPerson =
                crewPersonRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CREW_PERSON_NOT_FOUND));
        crewPersonRepository.delete(crewPerson);
    }
}
