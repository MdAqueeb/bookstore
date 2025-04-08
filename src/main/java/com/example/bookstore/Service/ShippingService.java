package com.example.bookstore.Service;

import com.example.bookstore.Entities.Shipping;
import com.example.bookstore.Entities.Order;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Exception.ResourceNotFoundException;
import com.example.bookstore.Exception.UnauthorizedException;
import com.example.bookstore.Repository.ShippingRepository;
import com.example.bookstore.Repository.UserRepo;
import com.example.bookstore.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepo userRepository;

    @Transactional
    public Shipping createShipping(Long orderId, Shipping shipping, String sellerEmail) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        User seller = userRepository.findByEmail(sellerEmail)
            .orElseThrow(() -> new ResourceNotFoundException("Seller not found with email: " + sellerEmail));

        // Verify seller owns the order
        if (!(order.getAdmin().getUserid() == (seller.getUserid()))) {
            throw new UnauthorizedException("Seller is not authorized to create shipping for this order");
        }

        shipping.setOrder(order);
        shipping.setSeller(seller);
        return shippingRepository.save(shipping);
    }

    public Shipping getShippingByOrderId(Long orderId, String userEmail) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        // Verify user has access to the order
        if (!(order.getUser().getUserid() == (user.getUserid())) && 
            !(order.getAdmin().getUserid() == (user.getUserid())) && 
            !user.getRole().equals(User.Role.ADMIN)) {
            throw new UnauthorizedException("User is not authorized to view this shipping information");
        }

        return shippingRepository.findByOrderOrderid(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Shipping not found for order: " + orderId));
    }

    @Transactional
    public Shipping updateTrackingInfo(Long orderId, String trackingNumber, Shipping.ShippingStatus status, String sellerEmail) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        User seller = userRepository.findByEmail(sellerEmail)
            .orElseThrow(() -> new ResourceNotFoundException("Seller not found with email: " + sellerEmail));

        // Verify seller owns the order
        if (!(order.getAdmin().getUserid() == (seller.getUserid()))) {
            throw new UnauthorizedException("Seller is not authorized to update shipping for this order");
        }

        Shipping shipping = shippingRepository.findByOrderOrderid(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Shipping not found for order: " + orderId));

        shipping.setTrackingNumber(trackingNumber);
        shipping.setStatus(status);
        return shippingRepository.save(shipping);
    }

    public Shipping getShippingByTrackingNumber(String trackingNumber) {
        return shippingRepository.findByTrackingNumber(trackingNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Shipping not found with tracking number: " + trackingNumber));
    }

    public List<Shipping> getShipmentsBySellerEmail(String sellerEmail) {
        User seller = userRepository.findByEmail(sellerEmail)
            .orElseThrow(() -> new ResourceNotFoundException("Seller not found with email: " + sellerEmail));

        return shippingRepository.findBySellerUserid(seller.getUserid());
    }
} 