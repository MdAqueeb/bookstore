package com.example.bookstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.Entities.Order;
import com.example.bookstore.Entities.Response;
import com.example.bookstore.Service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER','SELLER')")
    public ResponseEntity<Response> createOrder() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Yes authenticated");
            String email = auth.getName();
            Order order = orderService.createOrder(email);
            
            // System.out.println()
            Response<Order> response = new Response<>(
                HttpStatus.CREATED.value(),
                "Order created successfully",
                order
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/create-single")
    @PreAuthorize("hasAnyRole('USER','SELLER')")
    public ResponseEntity<Response> createSingleItemOrder(
            @RequestParam Long bookId,
            @RequestParam Integer quantity) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            Order order = orderService.createSingleItemOrder(email, bookId, quantity);
            Response<Order> response = new Response<>(
                HttpStatus.CREATED.value(),
                "Single item order created successfully",
                order
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/my-orders")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Order>> getMyOrders() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            List<Order> orders = orderService.getOrdersByUserEmail(email);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SELLER')")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            Order order = orderService.getOrderById(orderId, email);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{orderId}/cancel")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response> cancelOrder(@PathVariable Long orderId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            orderService.cancelOrder(orderId, email);
            Response<Void> response = new Response<>(
                HttpStatus.OK.value(),
                "Order cancelled successfully",
                null
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam Order.OrderStatus status) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            Order order = orderService.updateOrderStatus(orderId, status, email);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
} 