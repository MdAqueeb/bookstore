package com.example.bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstore.Entities.Wishlist;

@Repository
public interface WishlistRepo extends JpaRepository<Wishlist,Long>{
    
}
