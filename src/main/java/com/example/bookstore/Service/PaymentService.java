// package com.example.bookstore.Service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.example.bookstore.DTO.PaymentRequest;
// import com.example.bookstore.DTO.PaymentResponse;
// import com.example.bookstore.Entities.Payment;
// import com.example.bookstore.Entities.Payment.PaymentStatus;
// import com.example.bookstore.Repository.PaymentRepository;

// import java.math.BigDecimal;
// import java.time.LocalDateTime;
// import java.util.UUID;

// @Service
// public class PaymentService {

//     @Autowired
//     private PaymentRepository paymentRepository;
    
//     // @Autowired
//     // private OrderRepository orderRepository;

//     @Transactional
//     public PaymentResponse processPayment(PaymentRequest request) {
//         try {
//             // For refund requests
//             if (request.isRefund()) {
//                 return processRefund(request);
//             }

//             // Validate payment details
//             if (!validatePayment(request)) {
//                 PaymentResponse errorResponse = new PaymentResponse();
//                 errorResponse.setSuccess(false);
//                 errorResponse.setStatus("FAILED");
//                 errorResponse.setMessage("Invalid payment details");
//                 return errorResponse;
//             }

//             // For new payment requests
//             PaymentResponse response = new PaymentResponse();
//             response.setSuccess(true);
//             response.setPaymentId(generatePaymentId());
//             response.setTransactionId(generateTransactionId());
//             response.setStatus("SUCCESS");
//             response.setMessage("Payment processed successfully");

//             // Save payment record
//             Payment payment = Payment.builder()
//                 .paymentId(response.getPaymentId())
//                 .transactionId(response.getTransactionId())
//                 .paymentAmount(request.getAmount())
//                 .paymentStatus(PaymentStatus.valueOf(response.getStatus()))
//                 .paymentDate(LocalDateTime.now())
//                 .refunded(false)
//                 .build();

//             paymentRepository.save(payment);

//             return response;
//         } catch (Exception e) {
//             PaymentResponse errorResponse = new PaymentResponse();
//             errorResponse.setSuccess(false);
//             errorResponse.setStatus("FAILED");
//             errorResponse.setMessage("Payment processing failed: " + e.getMessage());
//             return errorResponse;
//         }
//     }

//     private PaymentResponse processRefund(PaymentRequest request) {
//         PaymentResponse response = new PaymentResponse();
        
//         try {
//             // Find original payment
//             Payment originalPayment = paymentRepository.findByPaymentId(request.getPaymentId())
//                 .orElseThrow(() -> new RuntimeException("Original payment not found"));

//             // Create refund response
//             response.setSuccess(true);
//             response.setPaymentId(generatePaymentId());
//             response.setTransactionId(generateTransactionId());
//             response.setStatus("REFUNDED");
//             response.setMessage("Refund processed successfully");

//             // Update original payment
//             originalPayment.setRefunded(true);
//             originalPayment.setPaymentStatus(PaymentStatus.valueOf("REFUNDED"));
//             paymentRepository.save(originalPayment);

//             // Save refund record
//             Payment refund = Payment.builder()
//                 .paymentId(response.getPaymentId())
//                 .transactionId(response.getTransactionId())
//                 .paymentAmount(originalPayment.getPaymentAmount().negate()) // Negative amount for refund
//                 // .currency(originalPayment.getCurrency())
//                 .paymentStatus(PaymentStatus.valueOf(response.getStatus()))
//                 // .description("Refund for payment: " + originalPayment.getPaymentId())
//                 .paymentDate(LocalDateTime.now())
//                 .refunded(true)
//                 .build();

//             paymentRepository.save(refund);

//         } catch (Exception e) {
//             response.setSuccess(false);
//             response.setStatus("FAILED");
//             response.setMessage("Refund processing failed: " + e.getMessage());
//         }

//         return response;
//     }

//     private String generatePaymentId() {
//         return "PAY-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
//     }

//     private String generateTransactionId() {
//         return "TXN-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
//     }

//     /**
//      * Validate payment details (mock implementation)
//      */
//     private boolean validatePayment(PaymentRequest request) {
//         // Simple validation logic
//         // In a real implementation, you would check with a payment gateway
        
//         // Mock validation: All cards are valid except those ending with "0000"
//         if (request.getCardNumber() != null && request.getCardNumber().endsWith("0000")) {
//             return false;
//         }
        
//         // Check expiry date format
//         if (request.getExpiryMonth() == null || request.getExpiryYear() == null) {
//             return false;
//         }
        
//         return true;
//     }
    
//     /**
//      * Get payment details by ID
//      */
//     public PaymentResponse getPaymentById(Long paymentId) {
//         Payment payment = paymentRepository.findById(paymentId)
//                 .orElseThrow(() -> new RuntimeException("Payment not found"));
        
//         PaymentResponse response = new PaymentResponse();
//         response.setPaymentId(String.valueOf(payment.getPaymentId()));
//         response.setOrderId(String.valueOf(payment.getOrders().getOrderid()));
//         response.setTransactionId(payment.getTransactionId());
//         response.setStatus(String.valueOf(payment.getPaymentStatus()));
//         response.setAmount(payment.getPaymentAmount());
//         response.setTimestamp(payment.getPaymentDate());
//         response.setMessage("Payment details retrieved successfully");
        
//         return response;
//     }
// } 