package com.example.bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstore.Entities.Books;

@Repository
public interface BookRepo extends JpaRepository<Books,Long>{
    
}
