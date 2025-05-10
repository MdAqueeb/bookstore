package com.example.bookstore.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

// import com.example.bookstore.DTO.OrderRequestDTO;
import com.example.bookstore.DTO.OrderResponseDTO;
import com.example.bookstore.DTO.RazorpaymentDTO;
// import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.Cart;
import com.example.bookstore.Entities.Order;
import com.example.bookstore.Entities.Payment;
// import com.example.bookstore.Entities.PurchasedBooks;
import com.example.bookstore.Service.CartService;
import com.example.bookstore.Service.MyBookService;
// import com.example.bookstore.Entities.OrderItem;
import com.example.bookstore.Service.OrderService;
import com.example.bookstore.Service.PayService;
import com.example.bookstore.Service.PaymentService;

// import io.jsonwebtoken.lang.Arrays;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;






@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService razorpayService;

    @Autowired
    private MyBookService mybook;

    @Autowired
    private CartService cartService;

    @Autowired
    private PaymentService paymentService;

    @Value("${razorpay.api.key}")
    private String  apikey;

    // // @PreAuthorize("hasAnyRole('USER','SELLER')")
    // public ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO orderrequest) {

    //     if(orderrequest.getCartItems() != null){
    //         return createOrderFromCart(orderrequest.getCartItems());
    //     }
    //     else if(orderrequest.getOrderItem() != null){
    //         return createItemOrder(orderrequest.getOrderItem());
    //     }
    //     else{
    //         return ResponseEntity.badRequest().body("Invalid order Request.");
    //     }

    // }

    @PostMapping("/create/{bookid}")
    private ResponseEntity<?> createItemOrder(@PathVariable long bookid) {
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Next move order");
            Order order = orderService.createSingleOrder(auth.getName(),bookid);
            if(order == null){
                return new ResponseEntity<>(order,HttpStatus.CONFLICT);
            }
            OrderResponseDTO res = new OrderResponseDTO();
            res.setAmount(order.getTotalAmount().multiply(BigDecimal.valueOf(100)).intValue());
            res.setKey(apikey);
            res.setRazorpayOrderId(order.getRazorpayOrderId());
            ArrayList<String> title = new ArrayList<>();
            title.add(order.getBook().getTitle());
            res.setBookTitle(title);
            return new ResponseEntity<>(res,HttpStatus.CREATED);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    private ResponseEntity<?> createOrderFromCart() {
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Order order = orderService.createOrderFromCart(auth.getName());
            if(order == null){
                return new ResponseEntity<>(order,HttpStatus.CONFLICT);
            }

            Cart cartItems = cartService.getCart(auth.getName());
            if(cartItems == null){
                return new ResponseEntity<>(order,HttpStatus.CONFLICT);
            } 
            
            OrderResponseDTO res = new OrderResponseDTO();
            res.setAmount(cartItems.getTotalAmount().multiply(BigDecimal.valueOf(100)).intValue());
            res.setRazorpayOrderId(order.getRazorpayOrderId());
            res.setKey(apikey);
            ArrayList<String> title = new ArrayList<>();
            for(int i = 0;i < cartItems.getBooks().size();i++){
                title.add(cartItems.getBooks().get(i).getTitle());
            }
            res.setBookTitle(title);
            cartService.ClearBooks(cartItems);

            return new ResponseEntity<>(res,HttpStatus.CREATED);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    // We have to add razorpay_payment_id to payment class
    @PostMapping("/verify-payment")
    private ResponseEntity<?> verifyPayment(@RequestBody RazorpaymentDTO dto){
        boolean verified = razorpayService.verifySignature(dto);
        Order order = orderService.updateOrderStatusToPaid(dto.getRazorpayOrderId());
        if(!verified){
            Payment payment = paymentService.AddpaymentDetails(order, "failed",dto);
            return new ResponseEntity<>(payment,HttpStatus.BAD_REQUEST);
        }

        
        if(order == null){
            
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } 

        Payment payment = paymentService.AddpaymentDetails(order,"paid",dto);
        mybook.AddBooks(order);
        return new ResponseEntity<>(payment,HttpStatus.OK);
    }

    @GetMapping("/getOrders")
    private ResponseEntity<List<Order>> getOrders(){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            List<Order> order = orderService.OrderList(auth.getName());
            if(order == null){
                return new ResponseEntity<>(order,HttpStatus.CONFLICT);
            } 
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{rzporderid}")
    public ResponseEntity<?> updateOrder(@PathVariable String rzporderid) {
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Order order = orderService.CancelOrder(auth.getName(),rzporderid);
            if(order == null){
                return new ResponseEntity<>(order,HttpStatus.CONFLICT);
            } 
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{rzporderid}")
    public ResponseEntity<?> getOrder(@PathVariable String rzporderid) {
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Wait getting Order");
            Order order = orderService.GetOrder(auth.getName(),rzporderid);
            if(order == null){
                return new ResponseEntity<>(order,HttpStatus.CONFLICT);
            } 
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    // @PostMapping("/create-single")
    // @PreAuthorize("hasAnyRole('USER','SELLER')")
    // public ResponseEntity<Response> createSingleItemOrder(
    //         @RequestParam Long bookId,
    //         @RequestParam Integer quantity) {
    //     try {
    //         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //         String email = auth.getName();
    //         Order order = orderService.createSingleItemOrder(email, bookId, quantity);
    //         Response<Order> response = new Response<>(
    //             HttpStatus.CREATED.value(),
    //             "Single item order created successfully",
    //             order
    //         );
    //         return ResponseEntity.status(HttpStatus.CREATED).body(response);
    //     } catch (Exception e) {
    //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    //     }
    // }

    // @GetMapping("/my-orders")
    // @PreAuthorize("hasRole('USER')")
    // public ResponseEntity<List<Order>> getMyOrders() {
    //     try {
    //         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //         String email = auth.getName();
    //         List<Order> orders = orderService.getOrdersByUserEmail(email);
    //         return ResponseEntity.ok(orders);
    //     } catch (Exception e) {
    //         throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    //     }
    // }

    // @GetMapping("/{orderId}")
    // @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SELLER')")
    // public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
    //     try {
    //         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //         String email = auth.getName();
    //         Order order = orderService.getOrderById(orderId, email);
    //         return ResponseEntity.ok(order);
    //     } catch (Exception e) {
    //         throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    //     }
    // }

    // @PutMapping("/{orderId}/cancel")
    // @PreAuthorize("hasRole('USER')")
    // public ResponseEntity<Response> cancelOrder(@PathVariable Long orderId) {
    //     try {
    //         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //         String email = auth.getName();
    //         orderService.cancelOrder(orderId, email);
    //         Response<Void> response = new Response<>(
    //             HttpStatus.OK.value(),
    //             "Order cancelled successfully",
    //             null
    //         );
    //         return ResponseEntity.ok(response);
    //     } catch (Exception e) {
    //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    //     }
    // }

    // @PutMapping("/{orderId}/status")
    // @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    // public ResponseEntity<Order> updateOrderStatus(
    //         @PathVariable Long orderId,
    //         @RequestParam Order.OrderStatus status) {
    //     try {
    //         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //         String email = auth.getName();
    //         Order order = orderService.updateOrderStatus(orderId, status, email);
    //         return ResponseEntity.ok(order);
    //     } catch (Exception e) {
    //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    //     }
    // }
} 