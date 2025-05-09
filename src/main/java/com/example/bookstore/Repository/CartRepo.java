package com.example.bookstore.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.Cart;
// import com.example.bookstore.Entities.User;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long>{

    @Query(value = "SELECT * FROM cart WHERE userid = :userid", nativeQuery = true)
    Optional<Cart> findByUserId(@Param("userid") long userid);


}
