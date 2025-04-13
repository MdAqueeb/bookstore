// package com.example.bookstore.Controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.server.ResponseStatusException;

// import com.example.bookstore.Entities.Shipping;
// import com.example.bookstore.Entities.Response;
// import com.example.bookstore.Service.ShippingService;

// import java.util.List;

// @RestController
// @RequestMapping("/api/shipping")
// public class ShippingController {

//     @Autowired
//     private ShippingService shippingService;

//     @PostMapping("/{orderId}/create")
//     @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
//     public ResponseEntity<Response> createShipping(@PathVariable Long orderId, @RequestBody Shipping shipping) {
//         try {
//             Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//             String email = auth.getName();
//             Shipping createdShipping = shippingService.createShipping(orderId, shipping, email);
//             Response<Shipping> response = new Response<>(
//                 HttpStatus.CREATED.value(),
//                 "Shipping information created successfully",
//                 createdShipping
//             );
//             return ResponseEntity.status(HttpStatus.CREATED).body(response);
//         } catch (Exception e) {
//             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
//         }
//     }

//     @GetMapping("/{orderId}")
//     @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SELLER')")
//     public ResponseEntity<Shipping> getShippingByOrderId(@PathVariable Long orderId) {
//         try {
//             Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//             String email = auth.getName();
//             Shipping shipping = shippingService.getShippingByOrderId(orderId, email);
//             return ResponseEntity.ok(shipping);
//         } catch (Exception e) {
//             throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//         }
//     }

//     @PutMapping("/{orderId}/tracking")
//     @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
//     public ResponseEntity<Shipping> updateTrackingInfo(
//             @PathVariable Long orderId,
//             @RequestParam String trackingNumber,
//             @RequestParam Shipping.ShippingStatus status) {
//         try {
//             Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//             String email = auth.getName();
//             Shipping shipping = shippingService.updateTrackingInfo(orderId, trackingNumber, status, email);
//             return ResponseEntity.ok(shipping);
//         } catch (Exception e) {
//             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
//         }
//     }

//     @GetMapping("/track/{trackingNumber}")
//     @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SELLER')")
//     public ResponseEntity<Shipping> trackShipment(@PathVariable String trackingNumber) {
//         try {
//             Shipping shipping = shippingService.getShippingByTrackingNumber(trackingNumber);
//             return ResponseEntity.ok(shipping);
//         } catch (Exception e) {
//             throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//         }
//     }

//     @GetMapping("/seller/orders")
//     @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
//     public ResponseEntity<List<Shipping>> getSellerShipments() {
//         try {
//             Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//             String email = auth.getName();
//             List<Shipping> shipments = shippingService.getShipmentsBySellerEmail(email);
//             return ResponseEntity.ok(shipments);
//         } catch (Exception e) {
//             throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//         }
//     }
// } 