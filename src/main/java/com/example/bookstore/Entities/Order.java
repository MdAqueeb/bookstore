package com.example.bookstore.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.CascadeType;

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

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @ToString.Exclude
    private User user;

    // @ManyToOne
    // @JoinColumn(name = "adminid")
    // @ToString.Exclude
    // private User admin;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    @JsonIgnore
    @ToString.Exclude
    private List<OrderItem> orderItems = new ArrayList<>();

    
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private String shippingAddress;

    // @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private String status = "Placed";

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal payment;
    private LocalDateTime orderDate;
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "cartid", nullable = true)
    @ToString.Exclude
    private Cart cart;

    // @OneToOne(mappedBy = "orders")
    // @ToString.Exclude
    // private Payment payment;

    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // public enum OrderStatus {
    //     PENDING,
    //     PAID,
    //     PROCESSING,
    //     SHIPPED,
    //     DELIVERED,
    //     CANCELLED,
    //     REFUNDED
    // }
}
