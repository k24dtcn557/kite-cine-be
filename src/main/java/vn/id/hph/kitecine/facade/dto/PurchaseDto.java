package vn.id.hph.kitecine.facade.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record PurchaseDto(
        String code,
        BigDecimal grandTotal,
        Instant expirationTime,
        Instant createdAt,
        Instant updatedAt,
        String status) {}
