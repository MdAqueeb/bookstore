package com.example.bookstore.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.DTO.RazorpaymentDTO;
// import com.example.bookstore.DTO.PaymentRequest;
// import com.example.bookstore.DTO.PaymentResponse;
import com.example.bookstore.Entities.Order;
import com.example.bookstore.Entities.Payment;
import com.example.bookstore.Entities.Payment.PaymentStatus;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Repository.PaymentRepository;
import com.example.bookstore.Repository.UserRepo;

import java.util.ArrayList;
import java.util.List;
// import java.math.BigDecimal;
// import java.time.LocalDateTime;
// import java.util.UUID;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepo userRepo;

    public Payment AddpaymentDetails(Order order, String status, RazorpaymentDTO dto) {
        Payment payment = new Payment();
        payment.setAmount(order.getTotalAmount());
        payment.setBuyerEmail(order.getUser());
        List<User> sel = new ArrayList<>();
        if(order.getBook() == null && order.getCart() != null){
            
            for(int i = 0;i < order.getCart().getBooks().size();i++){
                sel.add(order.getCart().getBooks().get(i).getSeller());
            }
            payment.setSellerEmail(sel);
        }
        else if(order.getBook() != null){
            sel.add(order.getBook().getSeller());
            payment.setSellerEmail(sel);
            System.out.println(sel);
        }
        
        payment.setRazorpayOrderid(order.getRazorpayOrderId());
        if(status.equals("paid")){
            payment.setPaymentStatus(PaymentStatus.PAID);
        }
        else if(status.equals("failed")){
            payment.setPaymentStatus(PaymentStatus.FAILED);
        }

        payment.setRazorpayPaymentid(dto.getRazorpayPaymentId());
        
        return paymentRepository.save(payment);
    }

    public List<Payment> GetPayments(String name) {
        Optional<User> usr = userRepo.findByEmail(name);
        if(!usr.isPresent()){
            return null;
        }
        Optional<List<Payment>> payment = paymentRepository.findByUserid(usr.get().getUserid());
        return payment.get();
    }
    
} 