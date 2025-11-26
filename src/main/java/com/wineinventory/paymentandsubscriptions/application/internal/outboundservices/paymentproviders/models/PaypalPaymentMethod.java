package com.wineinventory.paymentandsubscriptions.application.internal.outboundservices.paymentproviders.models;

public class PaypalPaymentMethod {
    
    private String type;
    private String cardType;
    private String lastDigits;
    private String expiryMonth;
    private String expiryYear;
    private String payerId;
    private String payerEmail;
    private String payerName;
    
    public PaypalPaymentMethod() {}
    
    public PaypalPaymentMethod(String type, String payerId, String payerEmail, String payerName) {
        this.type = type;
        this.payerId = payerId;
        this.payerEmail = payerEmail;
        this.payerName = payerName;
    }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }
    
    public String getLastDigits() { return lastDigits; }
    public void setLastDigits(String lastDigits) { this.lastDigits = lastDigits; }
    
    public String getExpiryMonth() { return expiryMonth; }
    public void setExpiryMonth(String expiryMonth) { this.expiryMonth = expiryMonth; }
    
    public String getExpiryYear() { return expiryYear; }
    public void setExpiryYear(String expiryYear) { this.expiryYear = expiryYear; }
    
    public String getPayerId() { return payerId; }
    public void setPayerId(String payerId) { this.payerId = payerId; }
    
    public String getPayerEmail() { return payerEmail; }
    public void setPayerEmail(String payerEmail) { this.payerEmail = payerEmail; }
    
    public String getPayerName() { return payerName; }
    public void setPayerName(String payerName) { this.payerName = payerName; }
    

    public boolean isPayPal() {
        return "PAYPAL".equals(type);
    }
    
    public boolean isCard() {
        return "CARD".equals(type);
    }
    
    public String getMaskedCardNumber() {
        if (isCard() && lastDigits != null) {
            return "****-****-****-" + lastDigits;
        }
        return null;
    }
}
