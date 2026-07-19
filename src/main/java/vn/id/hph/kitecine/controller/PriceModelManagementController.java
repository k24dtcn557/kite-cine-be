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
import vn.id.hph.kitecine.controller.param.KeywordStatusSearchParam;
import vn.id.hph.kitecine.controller.param.PriceModelParam;
import vn.id.hph.kitecine.controller.reponse.ApiResponse;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.facade.PriceModelFacade;
import vn.id.hph.kitecine.facade.dto.PriceModelDto;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PriceModelManagementController {
    PriceModelFacade priceModelFacade;

    @PostMapping("/price-models")
    public ApiResponse<PriceModelDto> createPriceModel(@RequestBody PriceModelParam param) {
        return ApiResponse.<PriceModelDto>builder()
                .result(priceModelFacade.createPriceModel(param))
                .build();
    }

    @GetMapping("/price-models")
    public ApiResponse<List<PriceModelDto>> getPriceModels() {
        return ApiResponse.<List<PriceModelDto>>builder()
                .result(priceModelFacade.getPriceModels())
                .build();
    }

    @PostMapping("/price-models/search")
    public ApiResponse<PageResponse<PriceModelDto>> searchPriceModels(@RequestBody KeywordStatusSearchParam param) {
        return ApiResponse.<PageResponse<PriceModelDto>>builder()
                .result(priceModelFacade.searchPriceModels(param))
                .build();
    }

    @GetMapping("/price-models/{id}")
    public ApiResponse<PriceModelDto> getPriceModel(@PathVariable Long id) {
        return ApiResponse.<PriceModelDto>builder()
                .result(priceModelFacade.getPriceModel(id))
                .build();
    }

    @DeleteMapping("/price-models/{id}")
    public ApiResponse<Void> deletePriceModel(@PathVariable Long id) {
        priceModelFacade.deletePriceModel(id);
        return ApiResponse.<Void>builder().build();
    }

    @PutMapping("/price-models/{id}")
    public ApiResponse<PriceModelDto> updatePriceModel(@PathVariable Long id, @RequestBody PriceModelParam param) {
        return ApiResponse.<PriceModelDto>builder()
                .result(priceModelFacade.updatePriceModel(id, param))
                .build();
    }
}
