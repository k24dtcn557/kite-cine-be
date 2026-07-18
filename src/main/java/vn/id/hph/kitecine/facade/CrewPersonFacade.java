package vn.id.hph.kitecine.facade;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.param.CrewPersonParam;
import vn.id.hph.kitecine.controller.param.KeywordStatusSearchParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.facade.dto.CrewPersonDto;
import vn.id.hph.kitecine.mapper.CrewPersonMapper;
import vn.id.hph.kitecine.service.CrewPersonService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CrewPersonFacade {
    CrewPersonService crewPersonService;
    CrewPersonMapper crewPersonMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public CrewPersonDto createCrewPerson(CrewPersonParam param) {
        var person = crewPersonService.create(param);
        return crewPersonMapper.toCrewPersonDto(person);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<CrewPersonDto> getCrewPersons() {
        var list = crewPersonService.getAll();
        return crewPersonMapper.toCrewPersonDtoList(list);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<CrewPersonDto> searchCrewPersons(KeywordStatusSearchParam param) {
        var page = crewPersonService.search(param);
        var dtos = crewPersonMapper.toCrewPersonDtoList(page.getData());

        return PageResponse.<CrewPersonDto>builder()
                .data(dtos)
                .pageNumber(page.getPageNumber())
                .pageSize(page.getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CrewPersonDto updateCrewPerson(Long id, CrewPersonParam param) {
        var person = crewPersonService.update(id, param);
        return crewPersonMapper.toCrewPersonDto(person);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CrewPersonDto getCrewPerson(Long id) {
        var person = crewPersonService.get(id);
        return crewPersonMapper.toCrewPersonDto(person);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCrewPerson(Long id) {
        crewPersonService.delete(id);
    }
}
