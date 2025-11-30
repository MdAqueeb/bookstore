package com.example.bookstore.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
// import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
// import jakarta.persistence.CascadeType;

@Entity
@Data
@Table(name = "orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    @ToString.Exclude
    @NotNull
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookid")
    @ToString.Exclude
    @NotNull
    private Books book ;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    @Column(unique = true)
    private String razorpayOrderId;
    // @Column(nullable = false)
    // private String razorpayPaymentId;
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "cartid", nullable = true)
    @ToString.Exclude
    private Cart cart ;

    // @OneToOne
    // @JoinColumn(name = "paymentid", nullable = false)
    // private Payment payments;


    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();

    }

    public enum OrderStatus {
        PENDING,
        PAID,
        CANCELLED,
    }

    public boolean isCartOrder(){
        return cart != null && book == null;
    }

    public boolean isSingleItemOrder(){
        return book != null && cart == null;
    }
}
