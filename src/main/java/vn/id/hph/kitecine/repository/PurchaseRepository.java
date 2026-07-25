package vn.id.hph.kitecine.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.id.hph.kitecine.entity.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>, JpaSpecificationExecutor<Purchase> {
    Optional<Purchase> findByCode(String code);

    @Modifying
    @Query("DELETE FROM Purchase p WHERE p.status = 'PENDING' AND p.expirationTime <= :time")
    int deleteExpiredPurchases(Instant time);
}
