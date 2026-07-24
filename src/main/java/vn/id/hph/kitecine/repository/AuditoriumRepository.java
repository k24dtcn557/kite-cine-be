package vn.id.hph.kitecine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.id.hph.kitecine.entity.Auditorium;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long>, JpaSpecificationExecutor<Auditorium> {
    int countByCinema_Id(Long id);

    int countAllByStatusAndCinema_Status(String status, String cinemaStatus);

    List<Auditorium> findAllByStatusOrderByIdDesc(String status);
}
