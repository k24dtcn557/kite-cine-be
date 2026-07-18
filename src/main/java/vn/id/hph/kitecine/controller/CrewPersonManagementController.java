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
import vn.id.hph.kitecine.controller.param.CrewPersonParam;
import vn.id.hph.kitecine.controller.param.KeywordStatusSearchParam;
import vn.id.hph.kitecine.controller.reponse.ApiResponse;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.facade.CrewPersonFacade;
import vn.id.hph.kitecine.facade.dto.CrewPersonDto;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CrewPersonManagementController {
    CrewPersonFacade crewPersonFacade;

    @PostMapping("/movies/crew-persons")
    public ApiResponse<CrewPersonDto> createCrewPerson(@RequestBody CrewPersonParam param) {
        return ApiResponse.<CrewPersonDto>builder()
                .result(crewPersonFacade.createCrewPerson(param))
                .build();
    }

    @GetMapping("/movies/crew-persons")
    public ApiResponse<List<CrewPersonDto>> getCrewPersons() {
        return ApiResponse.<List<CrewPersonDto>>builder()
                .result(crewPersonFacade.getCrewPersons())
                .build();
    }

    @PostMapping("/movies/crew-persons/search")
    public ApiResponse<PageResponse<CrewPersonDto>> searchCrewPersons(@RequestBody KeywordStatusSearchParam param) {
        return ApiResponse.<PageResponse<CrewPersonDto>>builder()
                .result(crewPersonFacade.searchCrewPersons(param))
                .build();
    }

    @GetMapping("/movies/crew-persons/{id}")
    public ApiResponse<CrewPersonDto> getCrewPerson(@PathVariable Long id) {
        return ApiResponse.<CrewPersonDto>builder()
                .result(crewPersonFacade.getCrewPerson(id))
                .build();
    }

    @DeleteMapping("/movies/crew-persons/{id}")
    public ApiResponse<Void> deleteCrewPerson(@PathVariable Long id) {
        crewPersonFacade.deleteCrewPerson(id);
        return ApiResponse.<Void>builder().build();
    }

    @PutMapping("/movies/crew-persons/{id}")
    public ApiResponse<CrewPersonDto> updateCrewPerson(@PathVariable Long id, @RequestBody CrewPersonParam param) {
        return ApiResponse.<CrewPersonDto>builder()
                .result(crewPersonFacade.updateCrewPerson(id, param))
                .build();
    }
}
