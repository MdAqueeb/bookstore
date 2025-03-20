package com.example.bookstore.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;

    // Relationship with the Order table (one-to-one)
    @OneToOne
    @JoinColumn(name = "orderid")
    private Order orders;

    // Payment Method (e.g., Credit Card, PayPal)
    @Column(nullable = false)
    private String paymentMethod;

    // Payment Status (e.g., Success, Failed, Pending)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    // Payment Amount (amount paid for the order)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal paymentAmount;

    // Transaction ID (from the payment gateway)
    @Column(nullable = false)
    private String transactionId;

    // Payment Date and Time
    @Column(nullable = false)
    private LocalDateTime paymentDate;

    // Enum for payment statuses
    public enum PaymentStatus {
        PENDING, SUCCESS, FAILED, CANCELLED
    }

    // Automatically set the payment date before persisting the entity
    @PrePersist
    protected void onCreate() {
        this.paymentDate = LocalDateTime.now();
    }
}
