package vn.id.hph.kitecine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.id.hph.kitecine.entity.Seat;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>, JpaSpecificationExecutor<Seat> {
    boolean existsByAuditorium_IdAndRowLetter(Long id, String rowLetter);

    List<Seat> findByAuditorium_Id(Long auditoriumId);
}
