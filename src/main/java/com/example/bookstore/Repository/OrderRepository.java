package com.example.bookstore.Repository;

// import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.Order;
// import com.example.bookstore.Entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM orders WHERE userid = :userId", nativeQuery = true)
    List<Order> findByUserUserid(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM orders WHERE razorpay_order_id = :rzpOrderId", nativeQuery = true)
    Order findByRazorpayOrderId(String rzpOrderId);

    @Query(value = "SELECT * FROM orders WHERE userid = :user and bookid = :books",nativeQuery = true)
    Order findByUseridBookid(@Param("user") Long user,@Param("books") Long books);
} 