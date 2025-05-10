package com.example.bookstore.Service;

import org.springframework.stereotype.Service;

// import com.example.bookstore.DTO.OrderRequestDTO;
// import com.example.bookstore.DTO.OrderResponseDTO;
// import com.example.bookstore.DTO.PaymentRequest;
// import com.example.bookstore.DTO.PaymentResponse;
import com.example.bookstore.Entities.*;
import com.example.bookstore.Entities.Order.OrderStatus;
// import com.example.bookstore.Exception.ResourceNotFoundException;
// import com.example.bookstore.Exception.UnauthorizedException;
import com.example.bookstore.Repository.*;
// import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// import com.example.bookstore.Service.PaymentService;
// import java.util.stream.Collectors;

import java.util.HashSet;
// import java.util.Iterator;
import java.util.List;
// import java.math.BigDecimal;
// import java.util.ArrayList;
// import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // @Autowired
    // private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepo cartRepository;

    // @Autowired
    // private OrderResponseDTO orderRequestDTO;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private BookRepo bookRepository;

    @Autowired
    private PayService razorpayOrderId;

    // private RazorpayClient client;

    // @Transactional
    // public Order createOrder(OrderRequestDTO vlue) {

    //     Optional<User> usr = userRepository.findById(vlue.getUserId());

        
    //     if(!usr.isPresent()){
    //         return null;
    //     }
    //     Optional<Cart> cart = cartRepository.findByUserId(usr.get().getUserid());
    //     if(!cart.isPresent()){
    //         return null;
    //     }
    //     else if(cart.get().getBooks().isEmpty()){
    //         return null;
    //     }
    //     Order order = new Order();

    //     order.setUser(usr.get());
    //     order.setCart(cart.get());  
        
        
    //     List<OrderItem> items = new ArrayList<>();
    //     for(int i = 0;i < vlue.getOrderItems().size();i++){
    //         OrderItem item = new OrderItem();
    //         Optional<Books> book = bookRepository.findById(vlue.getOrderItems().get(i).getBookId());
    //         if(!book.isPresent()){
    //             continue;
    //         }
    //         item.setBook(book.get());
    //         item.setOrder(order);
    //         item.setPrice(book.get().getPrice());
    //         User sellr = book.get().getSeller();
    //         item.getSeller();

    //     }

        
    //     return null;
    // }

    @Transactional
    public Order createSingleOrder(String email, long bookid) throws RazorpayException {
        Optional<User> usr = userRepository.findByEmail(email);
        Optional<Books> bok = bookRepository.findById(bookid);
        if(!usr.isPresent() || !bok.isPresent()){
            return null;
        }
        Order chk = orderRepository.findByUseridBookid(usr.get().getUserid(),bok.get().getBookid());
        if(chk != null && chk.getStatus() != OrderStatus.PAID){
            return chk;
        }
        else if(chk != null && chk.getStatus() == OrderStatus.PAID){
            return null;
        }
        Order order = new Order();
        order.setUser(usr.get());
        order.setBook(bok.get());
        order.setTotalAmount(bok.get().getPrice());

        System.out.println(order.getTotalAmount());

        com.razorpay.Order rzrorderid = razorpayOrderId.createOrder(order);

        order.setRazorpayOrderId(rzrorderid.get("id"));

        System.out.println(order.getRazorpayOrderId());
        order.setStatus(OrderStatus.PENDING);
        System.out.println(order.getStatus());
    
        return orderRepository.save(order);
    }

    // We have to remove cart items remaind me later 
    @Transactional
    public Order createOrderFromCart(String email) throws RazorpayException {
        Optional<User> usr =  userRepository.findByEmail(email);
        if(!usr.isPresent()){
            return null;
        }
        Optional<Cart> cartItems = cartRepository.findByUserId(usr.get().getUserid());
        if(!cartItems.isPresent()){
            return null;
        }
        List<Order> chk = cartItems.get().getOrders();
        Set<Long> orderedBookIds = new HashSet<>();

        if (!chk.isEmpty()) {
            for (Order order : chk) {
                if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CANCELLED) {
                    for (Books orderedBook : order.getCart().getBooks()) {
                        orderedBookIds.add(orderedBook.getBookid());
                    }
                }
            }
        }

        cartItems.get().getBooks().removeIf(cartBook -> orderedBookIds.contains(cartBook.getBookid()));
        Cart cart = cartRepository.save(cartItems.get());
        if(cart.getBooks().isEmpty()){
            return null;
        }
        Set<Long> cartBookIds = new HashSet<>();
        for (Books book : cart.getBooks()) {
            cartBookIds.add(book.getBookid());
        }
        
        // Loop through each order and remove matching books
        for (Order order : chk) {
            if (order.getStatus() == OrderStatus.PENDING || order.getStatus() == OrderStatus.CANCELLED) {
                List<Books> orderBooks = order.getCart().getBooks();
                orderBooks.removeIf(book -> cartBookIds.contains(book.getBookid()));
        
                if (orderBooks.isEmpty()) {
                    orderRepository.delete(order);
                } else {
                    com.razorpay.Order newRazorpayOrderId = razorpayOrderId.createOrder(order); // Your method to get new Razorpay order ID
                    order.setRazorpayOrderId(newRazorpayOrderId.get("id"));
                    orderRepository.save(order);
                }
            }
        }
        Order order = new Order();
        order.setUser(usr.get());
        order.setCart(cartItems.get());
        order.setTotalAmount(cartItems.get().getTotalAmount());
        System.out.println(order.getTotalAmount());
        com.razorpay.Order rzrorderid = razorpayOrderId.createOrder(order);
        order.setRazorpayOrderId(rzrorderid.get("id"));
        System.out.println(order.getRazorpayOrderId());
        order.setStatus(OrderStatus.PENDING);
    
        return orderRepository.save(order);

    }

    public Order updateOrderStatusToPaid(String rzpOrderId) {
        Order order = orderRepository.findByRazorpayOrderId(rzpOrderId);
        if(order == null){
            return null;
        }
        order.setStatus(OrderStatus.PAID);
        return orderRepository.save(order);
    }

    public List<Order> OrderList(String email) {
        Optional<User> usr = userRepository.findByEmail(email);
        if(!usr.isPresent()){
            return null;
        }
        List<Order> order = orderRepository.findByUserUserid(usr.get().getUserid());
        return order;
    }

    public Order CancelOrder(String email, String rzporderid) {
        Optional<User> usr = userRepository.findByEmail(email);
        if(!usr.isPresent()){
            return null;
        }
        Order order = orderRepository.findByRazorpayOrderId(rzporderid);
        if(order == null){
            return null;
        }
        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    public Order GetOrder(String email, String rzporderid) {
        Optional<User> usr = userRepository.findByEmail(email);
        if(!usr.isPresent()){
            return null;
        }
        System.out.println("Yes find user in orders");
        return orderRepository.findByRazorpayOrderId(rzporderid);
    }

}
