package com.example.bookstore.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;


@Data
public class PaymentResponse {
    private String paymentId;
    private String orderId;
    private boolean success;
    private String message;
    private String status;
    private String transactionId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
} 