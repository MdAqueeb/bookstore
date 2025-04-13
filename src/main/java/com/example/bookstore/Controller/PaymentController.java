// package com.example.bookstore.Controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.example.bookstore.DTO.PaymentRequest;
// import com.example.bookstore.DTO.PaymentResponse;
// import com.example.bookstore.Entities.Payment.PaymentStatus;
// import com.example.bookstore.Entities.Response;
// import com.example.bookstore.Service.PaymentService;

// @RestController
// @RequestMapping("/api/payments")
// public class PaymentController {

//     @Autowired
//     private PaymentService paymentservice;

//     /**
//      * Process a payment for an order
//      */
//     @PostMapping("/process")
//     public ResponseEntity<Response> processPayment(@RequestBody PaymentRequest paymentRequest) {
//         try {
//             PaymentResponse paymentResponse = paymentservice.processPayment(paymentRequest);
            
//             if (paymentResponse.getStatus().equals(PaymentStatus.SUCCESS)) {
//                 Response<PaymentResponse> response = new Response<>(
//                     HttpStatus.OK.value(),
//                     "Payment successful",
//                     paymentResponse
//                 );
//                 return ResponseEntity.ok(response);
//             } else {
//                 Response<PaymentResponse> response = new Response<>(
//                     HttpStatus.BAD_REQUEST.value(),
//                     paymentResponse.getMessage(),
//                     paymentResponse
//                 );
//                 return ResponseEntity.badRequest().body(response);
//             }
//         } catch (Exception e) {
//             Response<String> errorResponse = new Response<>(
//                 HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                 "An error occurred while processing payment: " + e.getMessage(),
//                 null
//             );
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//         }
//     }

//     /**
//      * Get payment details by ID
//      */
//     @GetMapping("/{paymentId}")
//     public ResponseEntity<Response> getPaymentById(@PathVariable Long paymentId) {
//         try {
//             PaymentResponse paymentResponse = paymentservice.getPaymentById(paymentId);
//             Response<PaymentResponse> response = new Response<>(
//                 HttpStatus.OK.value(),
//                 "Payment details retrieved successfully",
//                 paymentResponse
//             );
//             return ResponseEntity.ok(response);
//         } catch (Exception e) {
//             Response<String> errorResponse = new Response<>(
//                 HttpStatus.NOT_FOUND.value(),
//                 "Payment not found: " + e.getMessage(),
//                 null
//             );
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
//         }
//     }

//     /**
//      * Handle Stripe webhook notifications (for payment status updates)
//      */
//     @PostMapping("/webhook")
//     public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
//         // In a production environment, you would validate the webhook signature and process the event
//         // This is a simplified version
//         return ResponseEntity.ok("Webhook received");
//     }
// } 