package vn.id.hph.kitecine.facade;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.param.KeywordStatusSearchParam;
import vn.id.hph.kitecine.controller.param.PriceModelParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.facade.dto.PriceModelDto;
import vn.id.hph.kitecine.mapper.PriceModelMapper;
import vn.id.hph.kitecine.service.PriceModelService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PriceModelFacade {
    PriceModelService priceModelService;
    PriceModelMapper priceModelMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public PriceModelDto createPriceModel(PriceModelParam param) {
        var entity = priceModelService.create(param);
        return priceModelMapper.toPriceModelDto(entity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PriceModelDto> getPriceModels() {
        var list = priceModelService.getAll();
        return priceModelMapper.toPriceModelDtoList(list);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<PriceModelDto> searchPriceModels(KeywordStatusSearchParam param) {
        var page = priceModelService.search(param);
        var dtos = priceModelMapper.toPriceModelDtoList(page.getData());

        return PageResponse.<PriceModelDto>builder()
                .data(dtos)
                .pageNumber(page.getPageNumber())
                .pageSize(page.getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PriceModelDto updatePriceModel(Long id, PriceModelParam param) {
        var entity = priceModelService.update(id, param);
        return priceModelMapper.toPriceModelDto(entity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deletePriceModel(Long id) {
        priceModelService.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PriceModelDto getPriceModel(Long id) {
        var entity = priceModelService.get(id);
        return priceModelMapper.toPriceModelDto(entity);
    }
}
