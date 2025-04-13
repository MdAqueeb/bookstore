// package com.example.bookstore.Repository;

// import com.example.bookstore.Entities.Shipping;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

// import java.util.List;
// import java.util.Optional;

// @Repository
// public interface ShippingRepository extends JpaRepository<Shipping, Long> {

//     @Query(value = "SELECT * FROM shipping WHERE order_id = :orderId", nativeQuery = true)
//     Optional<Shipping> findByOrderOrderid(@Param("orderId") Long orderId);

//     @Query(value = "SELECT * FROM shipping WHERE LOWER(tracking_number) = LOWER(:trackingNumber)", nativeQuery = true)
//     Optional<Shipping> findByTrackingNumber(@Param("trackingNumber") String trackingNumber);

//     @Query(value = "SELECT * FROM shipping WHERE seller_id = :sellerId", nativeQuery = true)
//     List<Shipping> findBySellerUserid(@Param("sellerId") Long sellerId);
// } 