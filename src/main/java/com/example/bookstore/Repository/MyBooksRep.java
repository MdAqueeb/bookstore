package com.example.bookstore.Repository;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookstore.Entities.PurchasedBooks;

@Repository
public interface MyBooksRep extends JpaRepository<PurchasedBooks,Long>{

    @Query(value = "SELECT * FROM purchased_books WHERE userid =:userid",nativeQuery = true)
    PurchasedBooks findByUserId(@Param("userid") long userid);
    
}
