package vn.id.hph.kitecine.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.id.hph.kitecine.entity.ShowTime;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Long>, JpaSpecificationExecutor<ShowTime> {
    List<ShowTime> findAllByOrderByIdDesc();

    List<ShowTime> findByAuditorium_Id(Long auditoriumId);

    List<ShowTime> findByAuditorium_IdAndDate(Long auditoriumId, LocalDate date);
}
