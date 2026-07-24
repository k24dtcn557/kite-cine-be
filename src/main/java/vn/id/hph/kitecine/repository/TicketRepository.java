package vn.id.hph.kitecine.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.id.hph.kitecine.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {
    List<Ticket> findByPurchase_IdAndStatus(Long purchaseId, String status);

    List<Ticket> findByPurchase_Id(Long purchaseId);

    List<Ticket> findByShowtime_IdAndStatusIn(Long showtimeId, List<String> statuses);

    List<Ticket> findByBuyerIdAndStatusAndIdIn(String buyerId, String status, List<Long> ids);

    List<Ticket> findByBuyerIdAndStatusIn(String buyerId, List<String> statuses);

    List<Ticket> findByShowtime_IdAndBuyerIdAndStatusIn(long showTimeId, String userId, List<String> statuses);

    int countByStatusAndShowtime_Date(String status, LocalDate date);

    @Query("SELECT SUM(e.purchasePrice) FROM Ticket e WHERE e.status = :status AND e.showtime.date = :date")
    BigDecimal sumPurchasePriceByStatusAndShowtimeDate(@Param("status") String status, @Param("date") LocalDate date);

    @Query("SELECT SUM(e.purchasePrice) FROM Ticket e WHERE e.status = :status "
            + "AND e.showtime.date BETWEEN :fromDate AND :toDate")
    BigDecimal sumPurchasePriceByStatusAndShowtimeDates(
            @Param("status") String status, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query("SELECT SUM(e.purchasePrice) FROM Ticket e WHERE e.status = :status AND e.showtime.movie.id = :id")
    BigDecimal sumPurchasePriceByStatusAndMovieId(@Param("status") String status, @Param("id") Long id);

    @Query("SELECT SUM(e.purchasePrice) FROM Ticket e WHERE e.status = :status "
            + "AND e.showtime.auditorium.cinema.id = :cinemaId AND e.showtime.date BETWEEN :fromDate AND :toDate")
    BigDecimal sumPurchasePriceByStatusAndCinemaIdAndShowtimeDates(
            @Param("status") String status,
            @Param("cinemaId") Long cinemaId,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);
}
