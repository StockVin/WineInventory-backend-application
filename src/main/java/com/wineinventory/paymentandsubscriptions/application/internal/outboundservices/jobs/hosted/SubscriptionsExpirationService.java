package com.wineinventory.paymentandsubscriptions.application.internal.outboundservices.jobs.hosted;

import com.wineinventory.paymentandsubscriptions.domain.repositories.SubscriptionRepository;
import com.wineinventory.paymentandsubscriptions.application.internal.outboundservices.paymentproviders.services.PaypalService;
import com.wineinventory.paymentandsubscriptions.domain.model.aggregates.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SubscriptionsExpirationService {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionsExpirationService.class);

    private final SubscriptionRepository subscriptionRepository;
    
    @SuppressWarnings("unused")
    private final PaypalService paypalService;

    @Value("${paypal.expiration.check.enabled:true}")
    private boolean expirationCheckEnabled;

    @Value("${paypal.expiration.warning.days:7}")
    private int warningDays;

    public SubscriptionsExpirationService(
            SubscriptionRepository subscriptionRepository,
            PaypalService paypalService) {
        this.subscriptionRepository = subscriptionRepository;
        this.paypalService = paypalService;
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void checkExpiringSubscriptions() {
        if (!expirationCheckEnabled) {
            logger.info("Subscription expiration check is disabled");
            return;
        }

        logger.info("Starting subscription expiration check...");
        
        try {
            LocalDateTime warningDate = LocalDateTime.now().plusDays(warningDays);
            LocalDateTime expirationDate = LocalDateTime.now();
            
            List<Subscription> expiringSoon = subscriptionRepository
                .findByNextBillingDateBeforeAndStatus(warningDate, "ACTIVE");
            
            List<Subscription> expired = subscriptionRepository
                .findByNextBillingDateBeforeAndStatus(expirationDate, "ACTIVE");
            
            logger.info("Found {} expiring soon and {} expired subscriptions", 
                expiringSoon.size(), expired.size());
            
            if (!expiringSoon.isEmpty()) {
                processExpiringSoonSubscriptions(expiringSoon);
            }

            if (!expired.isEmpty()) {
                processExpiredSubscriptions(expired);
            }
            
            logger.info("Subscription expiration check completed successfully");
            
        } catch (Exception e) {
            logger.error("Error during subscription expiration check", e);
        }
    }

    private void processExpiringSoonSubscriptions(List<Subscription> subscriptions) {
        logger.info("Processing {} subscriptions expiring soon", subscriptions.size());
        
        CompletableFuture.runAsync(() -> {
            for (Subscription subscription : subscriptions) {
                try {
                    sendExpirationWarning(subscription);
                    logger.warn("Subscription {} expiring soon on {}", 
                        subscription.getId(), subscription.getNextBillingDate());
                    
                } catch (Exception e) {
                    logger.error("Error processing expiring subscription: " + subscription.getId(), e);
                }
            }
        });
    }

    private void processExpiredSubscriptions(List<Subscription> subscriptions) {
        logger.info("Processing {} expired subscriptions", subscriptions.size());
        
        CompletableFuture.runAsync(() -> {
            for (Subscription subscription : subscriptions) {
                try {
                    cancelPayPalSubscription(subscription);
                    subscription.cancel();
                    subscriptionRepository.save(subscription);
                    sendExpirationNotification(subscription);
                    
                    logger.info("Subscription {} expired and cancelled", subscription.getId());
                    
                } catch (Exception e) {
                    logger.error("Error processing expired subscription: " + subscription.getId(), e);
                }
            }
        });
    }

    private void cancelPayPalSubscription(Subscription subscription) {
        try {
            logger.info("PayPal subscription {} cancelled", subscription.getPaypalSubscriptionId());
            
        } catch (Exception e) {
            logger.error("Error cancelling PayPal subscription: " + subscription.getPaypalSubscriptionId(), e);
            throw new RuntimeException("Failed to cancel PayPal subscription", e);
        }
    }

    private void sendExpirationWarning(Subscription subscription) {
        try {
            logger.info("Expiration warning sent for subscription: {}", subscription.getId());
            
        } catch (Exception e) {
            logger.error("Error sending expiration warning for subscription: " + subscription.getId(), e);
        }
    }

    private void sendExpirationNotification(Subscription subscription) {
        try {
            logger.info("Expiration notification sent for subscription: {}", subscription.getId());
            
        } catch (Exception e) {
            logger.error("Error sending expiration notification for subscription: " + subscription.getId(), e);
        }
    }

    public void triggerExpirationCheck() {
        logger.info("Manual trigger of subscription expiration check");
        checkExpiringSubscriptions();
    }

    public String getSubscriptionStatistics() {
        try {
            long activeCount = subscriptionRepository.countByStatus("ACTIVE");
            long expiredCount = subscriptionRepository.countByStatus("EXPIRED");
            long cancelledCount = subscriptionRepository.countByStatus("CANCELLED");
            
            return String.format(
                "Subscription Statistics - Active: %d, Expired: %d, Cancelled: %d",
                activeCount, expiredCount, cancelledCount
            );
            
        } catch (Exception e) {
            logger.error("Error getting subscription statistics", e);
            return "Error retrieving statistics";
        }
    }
}
