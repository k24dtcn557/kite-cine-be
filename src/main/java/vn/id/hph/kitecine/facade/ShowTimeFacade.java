package vn.id.hph.kitecine.facade;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.param.ShowTimeParam;
import vn.id.hph.kitecine.controller.param.ShowTimeSearchParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.entity.Auditorium;
import vn.id.hph.kitecine.entity.PriceModel;
import vn.id.hph.kitecine.facade.dto.ShowTimeDto;
import vn.id.hph.kitecine.mapper.ShowTimeMapper;
import vn.id.hph.kitecine.service.AuditoriumService;
import vn.id.hph.kitecine.service.PriceModelService;
import vn.id.hph.kitecine.service.ShowTimeService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShowTimeFacade {
    ShowTimeService showTimeService;
    AuditoriumService auditoriumService;
    PriceModelService priceModelService;

    ShowTimeMapper showTimeMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public ShowTimeDto createShowTime(ShowTimeParam param) {
        Auditorium auditorium = auditoriumService.get(param.auditoriumId());
        PriceModel priceModel = priceModelService.get(param.priceModelId());

        var entity = showTimeService.create(auditorium, priceModel, param);
        return showTimeMapper.toShowTimeDto(entity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ShowTimeDto> getShowTimes() {
        var list = showTimeService.getAll();
        return showTimeMapper.toShowTimeDtoList(list);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ShowTimeDto> getShowTimesByAuditorium(Long auditoriumId) {
        var list = showTimeService.getByAuditoriumId(auditoriumId);
        return showTimeMapper.toShowTimeDtoList(list);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<ShowTimeDto> searchShowTimes(ShowTimeSearchParam param) {
        var page = showTimeService.search(param);
        var dtos = showTimeMapper.toShowTimeDtoList(page.getData());

        return PageResponse.<ShowTimeDto>builder()
                .data(dtos)
                .pageNumber(page.getPageNumber())
                .pageSize(page.getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ShowTimeDto updateShowTime(Long id, ShowTimeParam param) {
        Auditorium auditorium = auditoriumService.get(param.auditoriumId());
        PriceModel priceModel = priceModelService.get(param.priceModelId());

        var entity = showTimeService.update(id, auditorium, priceModel, param);
        return showTimeMapper.toShowTimeDto(entity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteShowTime(Long id) {
        showTimeService.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ShowTimeDto getShowTime(Long id) {
        var entity = showTimeService.get(id);
        return showTimeMapper.toShowTimeDto(entity);
    }
}
