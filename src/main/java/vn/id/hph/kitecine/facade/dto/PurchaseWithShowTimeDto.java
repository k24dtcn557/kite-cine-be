package vn.id.hph.kitecine.facade.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseWithShowTimeDto {
    String code;
    ShowTimeDetailDto showtime;
    BigDecimal grandTotal;
    Instant createdAt;
    Instant updatedAt;
    String status;
    List<TicketDto> tickets;
}
