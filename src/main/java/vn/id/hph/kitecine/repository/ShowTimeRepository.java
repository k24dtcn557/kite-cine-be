package vn.id.hph.kitecine.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.id.hph.kitecine.entity.Auditorium;
import vn.id.hph.kitecine.entity.ShowTime;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Long>, JpaSpecificationExecutor<ShowTime> {
    List<ShowTime> findAllByOrderByIdDesc();

    List<ShowTime> findByAuditorium_Id(Long auditoriumId);

    List<ShowTime> findByAuditorium_IdAndDate(Long auditoriumId, LocalDate date);

    List<ShowTime> findByMovie_IdAndDate(Long auditoriumId, LocalDate date);

    boolean existsByAuditoriumAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
            Auditorium auditorium, LocalDate date, LocalTime endTime, LocalTime startTime);

    boolean existsByAuditoriumAndDateAndIdNotAndStartTimeLessThanAndEndTimeGreaterThan(
            Auditorium auditorium, LocalDate date, Long id, LocalTime endTime, LocalTime startTime);

    List<ShowTime> findAllByDate(LocalDate date);
}
