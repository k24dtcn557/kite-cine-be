package vn.id.hph.kitecine.service;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import vn.id.hph.kitecine.configuration.CommonUtils;
import vn.id.hph.kitecine.entity.Purchase;
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

    public Purchase initializePurchase(BigDecimal grandTotal) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Instant newExpireTime = Instant.now().plusSeconds(reservedTime * 60);
        Purchase purchase = Purchase.builder()
                .code(CommonUtils.generateTicketCode())
                .buyerId(userId)
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
}
