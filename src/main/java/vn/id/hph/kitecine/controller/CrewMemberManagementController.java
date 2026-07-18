package vn.id.hph.kitecine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.id.hph.kitecine.controller.param.CrewMemberParam;
import vn.id.hph.kitecine.controller.param.CrewMemberSearchParam;
import vn.id.hph.kitecine.controller.reponse.ApiResponse;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.facade.CrewMemberFacade;
import vn.id.hph.kitecine.facade.dto.CrewMemberDto;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CrewMemberManagementController {
    CrewMemberFacade crewMemberFacade;

    @PostMapping("/movies/crew-members")
    public ApiResponse<CrewMemberDto> createCrewMember(@RequestBody CrewMemberParam param) {
        return ApiResponse.<CrewMemberDto>builder()
                .result(crewMemberFacade.createCrewMember(param))
                .build();
    }

    @GetMapping("/movies/crew-members")
    public ApiResponse<List<CrewMemberDto>> getCrewMembers() {
        return ApiResponse.<List<CrewMemberDto>>builder()
                .result(crewMemberFacade.getCrewMembers())
                .build();
    }

    @PostMapping("/movies/crew-members/search")
    public ApiResponse<PageResponse<CrewMemberDto>> searchCrewMembers(@RequestBody CrewMemberSearchParam param) {
        return ApiResponse.<PageResponse<CrewMemberDto>>builder()
                .result(crewMemberFacade.searchCrewMembers(param))
                .build();
    }

    @GetMapping("/movies/crew-members/{id}")
    public ApiResponse<CrewMemberDto> getCrewMember(@PathVariable Long id) {
        return ApiResponse.<CrewMemberDto>builder()
                .result(crewMemberFacade.getCrewMember(id))
                .build();
    }

    @DeleteMapping("/movies/crew-members/{id}")
    public ApiResponse<Void> deleteCrewMember(@PathVariable Long id) {
        crewMemberFacade.deleteCrewMember(id);
        return ApiResponse.<Void>builder().build();
    }

    @PutMapping("/movies/crew-members/{id}")
    public ApiResponse<CrewMemberDto> updateCrewMember(@PathVariable Long id, @RequestBody CrewMemberParam param) {
        return ApiResponse.<CrewMemberDto>builder()
                .result(crewMemberFacade.updateCrewMember(id, param))
                .build();
    }
}
