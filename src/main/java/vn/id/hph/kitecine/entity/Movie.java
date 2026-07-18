package vn.id.hph.kitecine.entity;

import java.time.LocalDate;

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
@Table(name = "movie")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movie extends AbstractAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "tagline", length = 1000)
    String tagline;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "genre")
    String genre;

    @Column(name = "runtime")
    Integer runtime;

    @Column(name = "release_date")
    LocalDate releaseDate;

    @Column(name = "highlighted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    boolean highlighted;

    @Column(name = "status")
    String status;
}
