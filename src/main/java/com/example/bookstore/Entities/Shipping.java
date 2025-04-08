package com.example.bookstore.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "shipping")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long shippingId;

    @OneToOne
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private Order order;

    private String trackingNumber;
    
    @Enumerated(EnumType.STRING)
    private ShippingStatus status;

    private String carrier;
    
    @Column(name = "estimated_delivery_date")
    private String estimatedDeliveryDate;
    
    @Column(name = "actual_delivery_date")
    private String actualDeliveryDate;
    
    @Column(name = "shipping_address")
    private String shippingAddress;
    
    @Column(name = "recipient_name")
    private String recipientName;
    
    @Column(name = "recipient_phone")
    private String recipientPhone;
    
    @ManyToOne
    @JoinColumn(name = "seller_id")
    @ToString.Exclude
    private User seller;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = ShippingStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum ShippingStatus {
        PENDING,
        PROCESSING,
        SHIPPED,
        IN_TRANSIT,
        DELIVERED,
        FAILED_DELIVERY,
        RETURNED
    }
} 