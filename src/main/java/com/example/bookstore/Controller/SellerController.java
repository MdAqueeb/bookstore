package com.example.bookstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.Response;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Service.SellerService;

import java.util.List;

@RestController
@RequestMapping("/seller")
@PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    // Add a new book listing
    @PostMapping("/books")
    public ResponseEntity<Books> addBook(@RequestBody Books book) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            Books savedBook = sellerService.addBook(book, email);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Get all books by current seller
    @GetMapping("/books")
    public ResponseEntity<List<Books>> getMyBooks() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            List<Books> books = sellerService.getBooksBySellerEmail(email);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // Update a book listing
    @PutMapping("/books/{id}")
    public ResponseEntity<Books> updateBook(@PathVariable long id, @RequestBody Books book) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            Books updatedBook = sellerService.updateBook(id, book, email);
            return ResponseEntity.ok(updatedBook);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Delete a book listing
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Response> deleteBook(@PathVariable long id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            sellerService.deleteBook(id, email);
            Response<String> response = new Response<>(HttpStatus.OK.value(), "Book deleted successfully", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Get seller profile
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            User seller = sellerService.getSellerProfile(email);
            return ResponseEntity.ok(seller);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
} 