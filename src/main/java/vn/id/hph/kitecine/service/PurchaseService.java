package vn.id.hph.kitecine.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import vn.id.hph.kitecine.configuration.CommonUtils;
import vn.id.hph.kitecine.entity.Purchase;
import vn.id.hph.kitecine.entity.ShowTime;
import vn.id.hph.kitecine.enums.PurchaseStatus;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;
import vn.id.hph.kitecine.repository.PurchaseRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PurchaseService {
    PurchaseRepository purchaseRepository;

    @NonFinal
    final long reservedTime = 5; // In Minutes

    public Purchase initializePurchase(ShowTime showTime, BigDecimal grandTotal) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Instant newExpireTime = Instant.now().plusSeconds(reservedTime * 60);
        Purchase purchase = Purchase.builder()
                .code(CommonUtils.generateTicketCode())
                .buyerId(userId)
                .showtime(showTime)
                .expirationTime(newExpireTime)
                .grandTotal(grandTotal)
                .status(PurchaseStatus.PENDING.name())
                .build();

        return purchaseRepository.save(purchase);
    }

    public Purchase get(String code) {
        return purchaseRepository.findByCode(code).orElseThrow(() -> new AppException(ErrorCode.PURCHASE_NOT_FOUND));
    }

    public Purchase pay(Purchase purchase) {
        purchase.setStatus(PurchaseStatus.PAID.name());
        return purchaseRepository.save(purchase);
    }

    public List<Purchase> getUpComingBookings() {
        LocalDate today = Instant.now().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDate();

        Specification<Purchase> query = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(root.get("showtime").get("date"), today));

            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdAt")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return purchaseRepository.findAll(query);
    }

    public List<Purchase> getPastBookings() {
        LocalDate today = Instant.now().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDate();

        Specification<Purchase> query = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.lessThan(root.get("showtime").get("date"), today));

            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdAt")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return purchaseRepository.findAll(query);
    }
}
