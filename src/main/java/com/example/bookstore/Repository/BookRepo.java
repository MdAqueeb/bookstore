package com.example.bookstore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookstore.Entities.Books;

@Repository
public interface BookRepo extends JpaRepository<Books, Long> {
    @Query(value = "SELECT * FROM books WHERE LOWER(author) LIKE LOWER(CONCAT('%', :author, '%'))", nativeQuery = true)
    List<Books> findByAuthorContainingIgnoreCase(@Param("author") String author);

    @Query(value = "SELECT * FROM books WHERE LOWER(title) LIKE LOWER(CONCAT('%', :title, '%'))", nativeQuery = true)
    List<Books> findByTitleContainingIgnoreCase(@Param("title") String title);
    
    @Query(value = "SELECT * FROM books WHERE approved = :approved", nativeQuery = true)
    List<Books> findByApproved(@Param("approved") String approved);

    @Query(value = "SELECT * FROM books WHERE approved = :approved ORDER BY price ASC", nativeQuery = true)
    List<Books> findByApprovedOrderByPriceAsc(@Param("approved") Books.Approved approved);

    @Query(value = "SELECT * FROM books WHERE approved = :pending ", nativeQuery = true)
    List<Books> findByPendingBooks(@Param("pending") String pending);

    // List<Books> findByApprovedOrderByPriceDesc(Boolean approved);
}
