package vn.id.hph.kitecine.controller.param;

import java.math.BigDecimal;
import java.util.Map;

public record PriceModelParam(String name, Map<String, BigDecimal> prices) {}
