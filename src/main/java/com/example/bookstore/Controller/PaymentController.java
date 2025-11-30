package com.example.bookstore.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import com.example.bookstore.Entities.Payment;
import com.example.bookstore.Service.PaymentService;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payment Endpoints", description = "All Payment Endpoints here")
public class PaymentController {

    @Autowired
    private PaymentService paymentservice;

    @GetMapping("/getPayments")
    private ResponseEntity<List<Payment>> getPayments(){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            List<Payment> payment = paymentservice.GetPayments(auth.getName());
            if(payment == null){
                return new ResponseEntity<>(payment,HttpStatus.CONFLICT);
            } 
            return new ResponseEntity<>(payment,HttpStatus.OK);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

} 