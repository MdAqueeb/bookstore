package com.example.bookstore.Repository;

import com.example.bookstore.Entities.Payment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    @Query(value = "SELECT * FROM payment WHERE razorpay_orderid = :razorpayOrderId",nativeQuery = true)
    Optional<Payment> findByRazorpayOrderid(@Param("razorpayOrderId") String razorpayOrderId);

    @Query(value = "SELECT * FROM payment WHERE buyid = :userId", nativeQuery = true)
    Optional<List<Payment>> findByUserid(@Param("userId") long userId);
} 