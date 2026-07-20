package vn.id.hph.kitecine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.id.hph.kitecine.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {
    List<Ticket> findByShowtime_IdAndStatusIn(Long showtimeId, List<String> statuses);

    List<Ticket> findBySeat_Id(Long seatId);

    List<Ticket> findByBuyerIdAndStatusIn(String buyerId, List<String> statuses);

    List<Ticket> findByStatus(String status);

    List<Ticket> findByShowtime_IdAndBuyerIdAndStatusIn(long showTimeId, String userId, List<String> statuses);
}
