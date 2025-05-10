package com.example.bookstore.Entities;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
// import jakarta.persistence.PreUpdate;
// import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
// @Builder
@Data
@NoArgsConstructor
@ToString(exclude = {"SellerEmail","buyerEmail"})
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentid;

    @ManyToOne
    @JoinColumn(name = "buyid", nullable = false)
    private User buyerEmail;
    
    
    @ManyToMany
    @JoinTable(
        name = "payment_seller",
        joinColumns = @JoinColumn(name = "payment_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "seller_id"))
    private List<User> SellerEmail;

    @Column(nullable = false)
    private String razorpayOrderid;

    @Column(name = "razorpay_payment_id")
    private String razorpayPaymentid;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
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