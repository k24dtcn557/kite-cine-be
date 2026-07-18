package vn.id.hph.kitecine.facade;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.param.CrewMemberParam;
import vn.id.hph.kitecine.controller.param.CrewMemberSearchParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.entity.CrewPerson;
import vn.id.hph.kitecine.entity.Movie;
import vn.id.hph.kitecine.facade.dto.CrewMemberDto;
import vn.id.hph.kitecine.mapper.CrewMemberMapper;
import vn.id.hph.kitecine.service.CrewMemberService;
import vn.id.hph.kitecine.service.CrewPersonService;
import vn.id.hph.kitecine.service.MovieService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CrewMemberFacade {
    CrewMemberService crewMemberService;
    MovieService movieService;
    CrewPersonService crewPersonService;

    CrewMemberMapper crewMemberMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public CrewMemberDto createCrewMember(CrewMemberParam param) {
        Movie movie = movieService.get(param.movieId());
        CrewPerson crewPerson = crewPersonService.get(param.crewPersonId());

        var crewMember = crewMemberService.create(movie, crewPerson, param);
        return crewMemberMapper.toCrewMemberDto(crewMember);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<CrewMemberDto> getCrewMembers() {
        var list = crewMemberService.getAll();
        return crewMemberMapper.toCrewMemberDtoList(list);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<CrewMemberDto> searchCrewMembers(CrewMemberSearchParam param) {
        var page = crewMemberService.search(param);
        var dtos = crewMemberMapper.toCrewMemberDtoList(page.getData());

        return PageResponse.<CrewMemberDto>builder()
                .data(dtos)
                .pageNumber(page.getPageNumber())
                .pageSize(page.getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CrewMemberDto updateCrewMember(Long id, CrewMemberParam param) {
        Movie movie = movieService.get(param.movieId());
        CrewPerson crewPerson = crewPersonService.get(param.crewPersonId());

        var crewMember = crewMemberService.update(id, movie, crewPerson, param);
        return crewMemberMapper.toCrewMemberDto(crewMember);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CrewMemberDto getCrewMember(Long id) {
        var crewMember = crewMemberService.get(id);
        return crewMemberMapper.toCrewMemberDto(crewMember);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCrewMember(Long id) {
        crewMemberService.delete(id);
    }
}
