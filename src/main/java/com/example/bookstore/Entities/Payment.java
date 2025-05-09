package com.example.bookstore.Entities;


import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
// import jakarta.persistence.PreUpdate;
// import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
// @Builder
@Data
@NoArgsConstructor
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentid;

    @ManyToOne
    @JoinColumn(name = "buyid", nullable = false)
    private User buyerEmail;
    
    @ManyToOne
    @JoinColumn(name = "sellerid", nullable = false)
    private User SellerEmail;

    @Column(nullable = false)
    private String razorpayOrderid;

    @Column(name = "razorpay_payment_id")
    private String razorpayPaymentid;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    private LocalDate createdAt;


    public enum PaymentStatus{
        PAID,
        FAILED,
        REFUND
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDate.now();
    }

}