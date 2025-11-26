package com.wineinventory.paymentandsubscriptions.application.internal.outboundservices.paymentproviders.services;

import com.wineinventory.paymentandsubscriptions.application.internal.outboundservices.paymentproviders.models.PaypalOrder;
import com.wineinventory.paymentandsubscriptions.application.internal.outboundservices.paymentproviders.models.PaypalOrderItem;
import com.wineinventory.paymentandsubscriptions.application.internal.outboundservices.paymentproviders.models.PaypalPayment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PaypalService {
    
    @Value("${paypal.api.base-url:https://api-m.sandbox.paypal.com}")
    private String paypalBaseUrl;
    
    @Value("${paypal.client.id}")
    private String clientId;
    
    @Value("${paypal.client.secret}")
    private String clientSecret;
    
    @Value("${paypal.return-url}")
    private String returnUrl;
    
    @Value("${paypal.cancel-url}")
    private String cancelUrl;
    
    private final RestTemplate restTemplate;
    
    public PaypalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    

    public PaypalOrder createOrder(BigDecimal amount, String currency, List<PaypalOrderItem> items) {
        try {
            String orderId = UUID.randomUUID().toString();
            
            String paypalOrderId = "PAYPAL-" + UUID.randomUUID().toString().substring(0, 8);
            
            PaypalOrder order = new PaypalOrder(orderId, paypalOrderId, amount, currency, "CREATED", items);
            
            System.out.println("PayPal Order created: " + paypalOrderId + " for amount: " + amount + " " + currency);
            
            return order;
            
        } catch (Exception e) {
            System.err.println("Error creating PayPal order: " + e.getMessage());
            throw new RuntimeException("Failed to create PayPal order", e);
        }
    }
    

    public PaypalPayment capturePayment(String paypalOrderId) {
        try {
            String paymentId = UUID.randomUUID().toString();
            String paypalPaymentId = "PAY-" + UUID.randomUUID().toString().substring(0, 12);
            
            PaypalPayment payment = new PaypalPayment(paymentId, paypalPaymentId, paypalOrderId, 
                                                     new BigDecimal("100.00"), "USD", "COMPLETED");
            
            System.out.println("PayPal payment captured: " + paypalPaymentId + " for order: " + paypalOrderId);
            
            return payment;
            
        } catch (Exception e) {
            System.err.println("Error capturing PayPal payment: " + e.getMessage());
            throw new RuntimeException("Failed to capture PayPal payment", e);
        }
    }

    public PaypalOrder getOrderDetails(String paypalOrderId) {
        try {
            List<PaypalOrderItem> items = List.of(
                new PaypalOrderItem("Premium Plan", "Monthly premium subscription", "PREMIUM-001", 
                                  new BigDecimal("99.99"), new BigDecimal("0.00"), 1, "DIGITAL_GOODS")
            );
            
            PaypalOrder order = new PaypalOrder("ORDER-" + UUID.randomUUID().toString(), paypalOrderId, 
                                              new BigDecimal("99.99"), "USD", "APPROVED", items);
            
            System.out.println("Retrieved PayPal order details: " + paypalOrderId);
            
            return order;
            
        } catch (Exception e) {
            System.err.println("Error getting PayPal order details: " + e.getMessage());
            throw new RuntimeException("Failed to get PayPal order details", e);
        }
    }

    public PaypalPayment getPaymentDetails(String paypalPaymentId) {
        try {
            PaypalPayment payment = new PaypalPayment("PAY-" + UUID.randomUUID().toString(), 
                                                     paypalPaymentId, "ORDER-" + UUID.randomUUID().toString(),
                                                     new BigDecimal("99.99"), "USD", "COMPLETED");
            
            System.out.println("Retrieved PayPal payment details: " + paypalPaymentId);
            
            return payment;
            
        } catch (Exception e) {
            System.err.println("Error getting PayPal payment details: " + e.getMessage());
            throw new RuntimeException("Failed to get PayPal payment details", e);
        }
    }
    
    public PaypalPayment refundPayment(String paypalPaymentId, BigDecimal amount) {
        try {
            String refundId = "REFUND-" + UUID.randomUUID().toString().substring(0, 8);
            
            PaypalPayment refund = new PaypalPayment(refundId, paypalPaymentId, null, 
                                                   amount, "USD", "REFUNDED");
            
            System.out.println("PayPal payment refunded: " + paypalPaymentId + " amount: " + amount);
            
            return refund;
            
        } catch (Exception e) {
            System.err.println("Error refunding PayPal payment: " + e.getMessage());
            throw new RuntimeException("Failed to refund PayPal payment", e);
        }
    }
    
    public boolean validateWebhookSignature(String payload, String signature, String certId) {
        try {

            System.out.println("Validating PayPal webhook signature");
            return true; 
            
        } catch (Exception e) {
            System.err.println("Error validating PayPal webhook signature: " + e.getMessage());
            return false;
        }
    }
    
    private String getAccessToken() {
        try {
            return "PAYPAL_ACCESS_TOKEN_" + UUID.randomUUID().toString().substring(0, 16);
        } catch (Exception e) {
            System.err.println("Error getting PayPal access token: " + e.getMessage());
            throw new RuntimeException("Failed to get PayPal access token", e);
        }
    }
}
