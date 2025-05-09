package com.example.bookstore.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.example.bookstore.DTO.PaymentRequest;
// import com.example.bookstore.DTO.PaymentResponse;
import com.example.bookstore.Entities.Order;
import com.example.bookstore.Entities.Payment;
import com.example.bookstore.Entities.Payment.PaymentStatus;
import com.example.bookstore.Repository.PaymentRepository;

// import java.math.BigDecimal;
// import java.time.LocalDateTime;
// import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment AddpaymentDetails(Order order, String status) {
        Payment payment = new Payment();
        payment.setAmount(order.getTotalAmount());
        payment.setBuyerEmail(order.getUser());
        payment.setBuyerEmail(order.getBook().getSeller());
        payment.setRazorpayOrderid(order.getRazorpayOrderId());
        if(status.equals("paid")){
            payment.setPaymentStatus(PaymentStatus.PAID);
        }
        else if(status.equals("failed")){
            payment.setPaymentStatus(PaymentStatus.FAILED);
        }
        
        return paymentRepository.save(payment);
    }
    
    // @Autowired
    // private OrderRepository orderRepository;
} 