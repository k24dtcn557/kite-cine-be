package vn.id.hph.kitecine.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "seat",
        indexes = {
            @Index(
                    name = "idx_seat_r_s_auditorium_id",
                    columnList = "auditorium_id, row_letter, seat_number",
                    unique = true)
        })
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Seat extends AbstractAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "row_letter", length = 2, nullable = false)
    String rowLetter;

    @Column(name = "seat_number", nullable = false)
    int seatNumber;

    @Column(name = "seat_type")
    String seatType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditorium_id", nullable = false)
    Auditorium auditorium;
}
