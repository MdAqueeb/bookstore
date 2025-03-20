package com.example.bookstore.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderid;

    @ManyToOne
    @JoinColumn(name = "userid",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "bookid",nullable = true)
    private Books books;

    // i have to store order time how i store
    @Column(nullable = false)
    private LocalDateTime ordertime;

    @OneToOne
    @JoinColumn(name = "cartid", nullable = false)  // This ensures that the order references the cart
    private Cart cart;

    @OneToOne(mappedBy = "orders")
    private ShippingDetails shippingDetails;

    @OneToOne(mappedBy = "orders")
    private Payment payment;

    @PrePersist
    protected void onCreate() {
        this.ordertime = LocalDateTime.now();
    }
}