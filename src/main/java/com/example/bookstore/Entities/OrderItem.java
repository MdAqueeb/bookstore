package com.example.bookstore.Entities;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemsid;

    @OneToOne
    @JoinColumn(name = "sellerid")
    @ToString.Exclude
    private User seller;

    @ManyToOne
    @JoinColumn(name = "orderid")
    @ToString.Exclude
    private Order order;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "bookid")
    private Books book;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;
} 