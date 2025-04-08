package com.example.bookstore.DTO;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentRequest {
    private Long orderId;
    private BigDecimal amount;
    private String paymentId;  // For refunds
    private boolean refund;
    private String currency = "USD";
    private String description;
    
    // Card validation fields
    private String cardNumber;
    private String expiryMonth;
    private String expiryYear;
    private String cvc;
    private String cardHolderName;
} 