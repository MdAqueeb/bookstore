// package com.example.bookstore.Repository;

// import com.example.bookstore.Entities.Payment;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

// import java.util.Optional;

// @Repository
// public interface PaymentRepository extends JpaRepository<Payment, Long> {

//     @Query(value = "SELECT * FROM payment WHERE payment_id = :paymentId", nativeQuery = true)
//     Optional<Payment> findByPaymentId(@Param("paymentId") String paymentId);

//     @Query(value = "SELECT * FROM payment WHERE orderid = :orderId", nativeQuery = true)
//     Optional<Payment> findByOrdersOrderid(@Param("orderId") Long orderId);
// } 