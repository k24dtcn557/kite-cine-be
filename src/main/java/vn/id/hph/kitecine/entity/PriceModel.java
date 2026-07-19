package vn.id.hph.kitecine.entity;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.id.hph.kitecine.enums.SeatType;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "price_model")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String name;

    @ElementCollection
    @CollectionTable(name = "price_model_items", joinColumns = @JoinColumn(name = "price_model_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "seat_type")
    @Column(name = "price", nullable = false)
    Map<SeatType, BigDecimal> prices = new EnumMap<>(SeatType.class);
}
