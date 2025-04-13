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
import java.util.ArrayList;
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
        if(!cart.isPresent()){
            return null;
        }
        else if(cart.get().getBooks().isEmpty()){
            return null;
        }
        Order order = new Order();

        order.setUser(usr.get());
        order.setCart(cart.get());  
        
        
        List<OrderItem> items = new ArrayList<>();
        for(int i = 0;i < vlue.getOrderItems().size();i++){
            OrderItem item = new OrderItem();
            Optional<Books> book = bookRepository.findById(vlue.getOrderItems().get(i).getBookId());
            if(!book.isPresent()){
                continue;
            }
            item.setBook(book.get());
            item.setOrder(order);
            item.setPrice(book.get().getPrice());
            User sellr = book.get().getSeller();
            item.getSeller();

        }

        
        return null;
    }

}
