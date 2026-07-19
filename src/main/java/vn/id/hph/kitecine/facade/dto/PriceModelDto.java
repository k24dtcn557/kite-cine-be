package vn.id.hph.kitecine.facade.dto;

import java.math.BigDecimal;
import java.util.Map;

public record PriceModelDto(long id, String name, Map<String, BigDecimal> prices) {}
