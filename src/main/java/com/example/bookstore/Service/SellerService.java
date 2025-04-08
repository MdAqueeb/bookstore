package com.example.bookstore.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Repository.BookRepo;
// import com.example.bookstore.Repository.BooksRepository;
import com.example.bookstore.Repository.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SellerService {

    @Autowired
    private BookRepo booksRepository;

    @Autowired
    private UserRepo userRepository;

    public Books addBook(Books book, String email) {
        User seller = userRepository.findByEmail(email).get();
        if(seller == null){
            throw new RuntimeException("User not found");
        }
        if (seller.getRole() != User.Role.SELLER && seller.getRole() != User.Role.ADMIN) {
            throw new RuntimeException("User is not authorized to sell books");
        }

        // Set initial approval status (auto-approve for admin, pending for sellers)
        if (seller.getRole() == User.Role.ADMIN) {
            book.setApproved(true);
        } else {
            book.setApproved(false);
        }
        
        // Associate the book with the seller
        if (seller.getBookListings() == null) {
            seller.setBookListings(new ArrayList<>());
        }
        
        // Save the book first
        Books savedBook = booksRepository.save(book);
        
        // Add the book to seller's listings
        seller.getBookListings().add(savedBook);
        userRepository.save(seller);
        
        return savedBook;
    }

    public List<Books> getBooksBySellerEmail(String email) {
        User seller = userRepository.findByEmail(email).get();
        if(seller == null){
            throw new RuntimeException("User not found");
        }
        return seller.getBookListings();
    }

    public Books updateBook(long bookId, Books updatedBook, String email) {
        User seller = userRepository.findByEmail(email).get();
        if(seller == null){
            throw new RuntimeException("User not found");
        }
        // Check if the book belongs to this seller
        Books existingBook = seller.getBookListings().stream()
                .filter(b -> b.getBookid() == bookId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not found or not owned by this seller"));
        
        // Update the book properties
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setDescription(updatedBook.getDescription());
        existingBook.setPrice(updatedBook.getPrice());
        existingBook.setImage(updatedBook.getImage());
        
        // Save the updated book
        return booksRepository.save(existingBook);
    }

    public void deleteBook(long bookId, String email) {
        User seller = userRepository.findByEmail(email).get();
        if(seller == null){
            throw new RuntimeException("User not found");
        }
        // Check if the book belongs to this seller
        Books bookToRemove = seller.getBookListings().stream()
                .filter(b -> b.getBookid() == bookId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not found or not owned by this seller"));
        
        // Remove the book from seller's listings
        seller.getBookListings().remove(bookToRemove);
        userRepository.save(seller);
        
        // Delete the book
        booksRepository.deleteById(bookId);
    }

    public User getSellerProfile(String email) {
        User seller = userRepository.findByEmail(email).get();
        if(seller == null){
            throw new RuntimeException("User not found");
        }
        return seller;
    }
} 