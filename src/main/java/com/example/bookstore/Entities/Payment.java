// package com.example.bookstore.Entities;

// import jakarta.persistence.*;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import lombok.ToString;

// import java.math.BigDecimal;
// import java.time.LocalDateTime;

// // @Entity
// // @Data
// // @Table(name = "payment")
// // @Builder
// // @NoArgsConstructor
// // @AllArgsConstructor
// // public class Payment {
// //     @Id
// //     @GeneratedValue(strategy = GenerationType.IDENTITY)
// //     private long payid;

// //     // Relationship with the Order table (one-to-one)
// //     @OneToOne
// //     @JoinColumn(name = "orderid")
// //     @ToString.Exclude
// //     private Order orders;

// //     // Payment Method (e.g., Credit Card, PayPal)
// //     @Column(nullable = false)
// //     private String paymentId;

// //     // Payment Status (e.g., Success, Failed, Pending)
// //     @Enumerated(EnumType.STRING)
// //     @ToString.Exclude
// //     @Column(nullable = false)
// //     @Builder.Default
// //     private PaymentStatus paymentStatus = PaymentStatus.PENDING;

// //     // Payment Amount (amount paid for the order)
// //     @Column(nullable = false, precision = 10, scale = 2)
// //     private BigDecimal paymentAmount;

// //     // Transaction ID (from the payment gateway)
// //     @Column(nullable = false)
// //     private String transactionId;

// //     // Payment Date and Time
// //     @Column(nullable = false)
// //     private LocalDateTime paymentDate;

// //     // Refunded flag
// //     @Column(nullable = false)
// //     @Builder.Default
// //     private boolean refunded = false;

// //     // Enum for payment statuses
// //     public enum PaymentStatus {
// //         PENDING, SUCCESS, FAILED, CANCELLED, REFUNDED
// //     }

// //     // Automatically set the payment date before persisting the entity
// //     @PrePersist
// //     protected void onCreate() {
// //         if (this.paymentDate == null) {
// //             this.paymentDate = LocalDateTime.now();
// //         }
// //     }
// // }

// @Entity
// @Data
// @Table(name = "payment")
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
// public class Payment {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private long payid;

//     @OneToOne
//     @JoinColumn(name = "orderid")
//     @ToString.Exclude
//     private Order orders;

//     @Column(nullable = false)
//     private String paymentId;

//     @Enumerated(EnumType.STRING)
//     @Column(nullable = false)
//     @Builder.Default
//     private PaymentStatus paymentStatus = PaymentStatus.PENDING;

//     @Column(nullable = false, precision = 10, scale = 2)
//     private BigDecimal paymentAmount;

//     @Column(nullable = false)
//     private String transactionId;

//     @Column(nullable = false)
//     private LocalDateTime paymentDate;

//     @Column(nullable = false)
//     @Builder.Default
//     private boolean refunded = false;

//     public enum PaymentStatus {
//         PENDING, SUCCESS, FAILED, CANCELLED, REFUNDED
//     }

//     @PrePersist
//     protected void onCreate() {
//         if (this.paymentDate == null) {
//             this.paymentDate = LocalDateTime.now();
//         }
//     }
// }

