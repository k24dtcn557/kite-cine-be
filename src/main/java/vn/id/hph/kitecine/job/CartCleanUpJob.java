package vn.id.hph.kitecine.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.service.PurchaseService;
import vn.id.hph.kitecine.service.TicketService;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartCleanUpJob {
    TicketService ticketService;
    PurchaseService purchaseService;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cleanUpExpiredCarts() {
        int cleanUpTickets = ticketService.cleanUpHoldings();
        log.info("Clean up expired tickets: {}", cleanUpTickets);
        int cleanUpPurchases = purchaseService.cleanUpExpiredPurchases();
        log.info("Clean up expired purchases: {}", cleanUpPurchases);
    }
}
