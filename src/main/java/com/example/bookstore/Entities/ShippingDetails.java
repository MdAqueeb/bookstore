package com.example.bookstore.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AllArgsConstructor;

@Entity
@Data
@Table(name = "shipping_details")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_details_id")
    private Long id;

    // The order this shipping detail belongs to
    @OneToOne
    @JoinColumn(name = "orderid")
    @ToString.Exclude
    private Order orders;

    // Shipping Address
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Shipping Method (Standard, Express, etc.)
    // @Column(nullable = false)
    // private String shippingMethod;

    // Shipping Carrier (FedEx, UPS, etc.)
    // @Column(nullable = true)
    // private String shippingCarrier;

    // // Tracking Number
    // @Column(nullable = true)
    // private String trackingNumber;

    // Shipping Status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ShippingStatus shippingStatus = ShippingStatus.PENDING;

    // Enum for shipping statuses
    public enum ShippingStatus {
        PENDING, SHIPPED, DELIVERED, CANCELED, RETURNED
    }

    // Shipping Cost
    @Column(nullable = true, precision = 10, scale = 2)
    private BigDecimal shippingCost ;

    // Optional: Estimated delivery date
    @Column(nullable = true)
    
    private LocalDateTime estimatedDeliveryDate;

}




