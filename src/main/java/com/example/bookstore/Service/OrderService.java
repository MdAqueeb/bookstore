package com.example.bookstore.Service;

import org.springframework.stereotype.Service;

import com.example.bookstore.DTO.OrderRequestDTO;
import com.example.bookstore.DTO.PaymentRequest;
import com.example.bookstore.DTO.PaymentResponse;
import com.example.bookstore.Entities.*;
import com.example.bookstore.Exception.ResourceNotFoundException;
import com.example.bookstore.Exception.UnauthorizedException;
import com.example.bookstore.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// import com.example.bookstore.Service.PaymentService;
import java.util.stream.Collectors;

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
    private OrderRequestDTO orderRequestDTO;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private BookRepo bookRepository;

    @Transactional
    public Order createOrder(OrderRequestDTO vlue) {

        Optional<User> usr = userRepository.findById(vlue.getUserId());

        
        if(!usr.isPresent()){
            return null;
        }
        Optional<Cart> cart = cartRepository.findByUserId(usr.get().getUserid());
        Order order = new Order();

        order.setUser(usr.get());
        order.setCart();

        
        return null;
    }

}
