package vn.id.hph.kitecine.facade.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record TicketDto(
        long id,
        long showtimeId,
        long seatId,
        BigDecimal purchasePrice,
        String buyerId,
        Instant expirationTime,
        String qrCode,
        String status,
        Instant createdAt,
        Instant updatedAt) {}
