package vn.id.hph.kitecine.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "show_time")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowTime extends AbstractAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "date")
    LocalDate date;

    @Column(name = "start_time")
    LocalTime startTime;

    @Column(name = "end_time")
    LocalTime endTime;

    @Column(name = "movie_id")
    Long movieId;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "genre")
    String genre;

    @Column(name = "runtime")
    Integer runtime;

    @Column(name = "poster", length = 2000)
    String poster;

    @Column(name = "background", length = 2000)
    String background;

    @Column(name = "video", length = 2000)
    String video;

    @Column(name = "release_date")
    LocalDate releaseDate;

    @Column(name = "highlighted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    boolean highlighted;

    @Column(name = "status")
    String status;
}
