package com.example.bookstore.Service;

import com.example.bookstore.DTO.PaymentRequest;
import com.example.bookstore.DTO.PaymentResponse;
import com.example.bookstore.Entities.*;
import com.example.bookstore.Exception.ResourceNotFoundException;
import com.example.bookstore.Exception.UnauthorizedException;
import com.example.bookstore.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.bookstore.Service.PaymentService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepo cartRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookRepo bookRepository;

    @Transactional
    public Order createOrder(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        // Get user's cart
        Cart cart = cartRepository.findByUserId(user.getUserid())
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userEmail));

        if (cart.getBooks().isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty");
        }

        // User admin = userRepository.findByRole(User.Role.ADMIN)
        // .stream().findFirst()
        // .orElseThrow(() -> new ResourceNotFoundException("Admin user not found"));
        Optional<List<User>> admins = userRepository.findByRole(User.Role.ADMIN);
        if (admins.get().size() != 1) {
            throw new ResourceNotFoundException("Exactly one admin is required.");
        }
        User admin = admins.get().get(0);
    
        // Create order
        System.out.println(admin.getName());
        Order order = Order.builder()
            .user(user)
            .status(Order.OrderStatus.PENDING)
            .cart(cart)  // Link the cart to the order
            .admin(admin)
            .build();

        System.out.println(order.getOrderid());
        // Calculate total amount and create order items
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Books cartItem : cart.getBooks()) {
            System.out.println(cartItem.getTitle());
            OrderItem orderItem = OrderItem.builder()
                .order(order)
                .book(cartItem)
                .seller(cartItem.getSeller())
                .price(cartItem.getPrice())
                .quantity(1)
                .subtotal(cartItem.getPrice())
                .build();
            if (orderItem.getSeller() == null) {
                throw new ResourceNotFoundException("Seller not found for book: " + cartItem.getTitle());
            }
            System.out.println("OrderItems 0 "+orderItem.getSeller());
            order.getOrderItems().add(orderItem);
            System.out.println(order);
            System.out.println(order.getOrderItems().get(0).getPrice());
            totalAmount = totalAmount.add(cartItem.getPrice());
        }


        order.setTotalAmount(totalAmount);  
        System.out.println(order.getOrderDate());
        // Process payment
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(totalAmount);
        paymentRequest.setOrderId(order.getOrderid());
        PaymentResponse payment = paymentService.processPayment(paymentRequest);
        order.setPaymentId(payment.getPaymentId());
        order.setStatus(Order.OrderStatus.PAID);

        System.out.println(paymentRequest.getCardNumber()+"\n"+order.getAdmin());
        // Save order and order items
        order = orderRepository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());

        System.out.println(order.getAdmin());
        // Clear cart items but keep the cart object
        cart.getBooks().clear();
        cartRepository.save(cart);
        System.out.println(cart.getTotalItems());
        return null;
    }

    @Transactional
    public Order createSingleItemOrder(String userEmail, Long bookId, Integer quantity) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        Books book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        // Create order
        Order order = Order.builder()
            .user(user)
            .status(Order.OrderStatus.PENDING)
            .build();

        // Create order item
        OrderItem orderItem = OrderItem.builder()
            .order(order)
            .book(book)
            .quantity(quantity)
            .price(book.getPrice())
            .subtotal(book.getPrice().multiply(BigDecimal.valueOf(quantity)))
            .build();

        order.getOrderItems().add(orderItem);
        // order.setTotalAmount(orderItem.getSubtotal());
        order.setAdmin(book.getSeller());

        // Process payment
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(order.getTotalAmount());
        paymentRequest.setOrderId(order.getOrderid());
        // Payment payment = paymentService.processPayment(paymentRequest);
        PaymentResponse payment = paymentService.processPayment(paymentRequest);
        order.setPaymentId(payment.getPaymentId());
        order.setStatus(Order.OrderStatus.PAID);

        // Save order and order item
        order = orderRepository.save(order);
        orderItemRepository.save(orderItem);

        return order;
    }

    public List<Order> getOrdersByUserEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));
            // orderRepository.findByUserId(user.getUserid());
        return orderRepository.findByUserUserid(user.getUserid());
    }

    public Order getOrderById(Long orderId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        // Verify user has access to the order
        if (!(order.getAdmin().getUserid() == user.getUserid()) && 
            !user.getRole().equals("ADMIN")) {
                System.out.println("This is True and True = True fix it");
            throw new UnauthorizedException("User is not authorized to view this order");
        }

        return order;
    }

    @Transactional
    public void processRefund(String paymentId) {
        try {
            // Create refund request
            PaymentRequest refundRequest = new PaymentRequest();
            refundRequest.setPaymentId(paymentId);
            refundRequest.setRefund(true); // Indicate this is a refund request

            // Process refund through payment service
            PaymentResponse refundResponse = paymentService.processPayment(refundRequest);

            // Verify refund was successful
            if (refundResponse == null || !refundResponse.isSuccess()) {
                throw new RuntimeException("Refund processing failed");
            }

            // Log refund success
            System.out.println("Refund processed successfully for payment ID: " + paymentId);
        } catch (Exception e) {
            // Log refund failure
            System.err.println("Failed to process refund for payment ID: " + paymentId + ". Error: " + e.getMessage());
            throw new RuntimeException("Failed to process refund: " + e.getMessage());
        }
    }

    @Transactional
    public void cancelOrder(Long orderId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        // Verify user owns the order
        if (!(order.getAdmin().getUserid() == user.getUserid())) {
            throw new UnauthorizedException("User is not authorized to cancel this order");
        }

        // Only allow cancellation of pending or paid orders
        if (order.getStatus() != Order.OrderStatus.PENDING && 
            order.getStatus() != Order.OrderStatus.PAID) {
            throw new UnauthorizedException("Order cannot be cancelled in its current status");
        }

        // Process refund if payment was made
        if (order.getStatus() == Order.OrderStatus.PAID) {
            processRefund(order.getPaymentId());
        }

        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        // Verify user is seller or admin
        if (!(order.getAdmin().getUserid() == (user.getUserid())) && !user.getRole().equals("ADMIN")) {
            throw new UnauthorizedException("User is not authorized to update order status");
        }

        order.setStatus(status);
        return orderRepository.save(order);
    }
}
