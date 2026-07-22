package vn.id.hph.kitecine.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import vn.id.hph.kitecine.entity.Purchase;
import vn.id.hph.kitecine.facade.dto.PurchaseDetailDto;
import vn.id.hph.kitecine.facade.dto.PurchaseDto;
import vn.id.hph.kitecine.facade.dto.PurchaseWithShowTimeDto;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {
    PurchaseDto toPurchaseDto(Purchase purchase);

    PurchaseDetailDto toPurchaseDetailDto(Purchase purchase);

    List<PurchaseWithShowTimeDto> toPurchaseWithShowTimeDtoList(List<Purchase> bookings);
}
