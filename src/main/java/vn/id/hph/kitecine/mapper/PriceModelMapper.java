package vn.id.hph.kitecine.mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import vn.id.hph.kitecine.controller.param.PriceModelParam;
import vn.id.hph.kitecine.entity.PriceModel;
import vn.id.hph.kitecine.enums.SeatType;
import vn.id.hph.kitecine.facade.dto.PriceModelDto;

@Mapper(componentModel = "spring")
public interface PriceModelMapper {
    PriceModel toPriceModel(PriceModelParam param);

    PriceModelDto toPriceModelDto(PriceModel entity);

    void update(@MappingTarget PriceModel entity, PriceModelParam param);

    List<PriceModelDto> toPriceModelDtoList(List<PriceModel> entities);

    // helpers to convert Map<String, BigDecimal> <-> Map<SeatType, BigDecimal>
    default Map<SeatType, BigDecimal> stringKeyToSeatTypeMap(Map<String, BigDecimal> map) {
        if (map == null) return new HashMap<>();
        Map<SeatType, BigDecimal> result = new java.util.EnumMap<>(SeatType.class);
        for (Map.Entry<String, BigDecimal> e : map.entrySet()) {
            if (e.getKey() == null) continue;
            try {
                SeatType st = SeatType.valueOf(e.getKey().toUpperCase());
                result.put(st, e.getValue());
            } catch (IllegalArgumentException ex) {
                // skip unknown key
            }
        }
        return result;
    }

    default Map<String, BigDecimal> seatTypeToStringMap(Map<SeatType, BigDecimal> map) {
        if (map == null) return new HashMap<>();
        return map.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().name(), Map.Entry::getValue));
    }
}
