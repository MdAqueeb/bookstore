package com.example.bookstore.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Service.BookService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class BooksController {

    @Autowired
    private BookService booklogic;

    @PostMapping("/AddBooks")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<Books> AddingBooks(@RequestBody Books entity) {
        try {
            // Get the authenticated user's email
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            
            // Add the book with the seller's email
            Books var = booklogic.AddBook(entity, email);
            if(var == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The values are not assigned to Database");
            }
            return new ResponseEntity<>(var, HttpStatus.CREATED);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error adding book: " + e.getMessage());
        }
    }

    @PostMapping("/AddAllBooks")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<List<Books>> AddingallBooks(@RequestBody List<Books> entity) {
        try {
            // Get the authenticated user's email
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            
            // Add the book with the seller's email
            List<Books> var = booklogic.AddlistBook(entity, email);
            if(var == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The values are not assigned to Database");
            }
            return new ResponseEntity<>(var, HttpStatus.CREATED);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error adding book: " + e.getMessage());
        }
    }

    @GetMapping("/AllBooks")
    public ResponseEntity<List<Books>> RetriveBooks() {
        List<Books> var = booklogic.getAll();
        if(var.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(var, HttpStatus.OK);
    }
    
    @GetMapping("/admin/books/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Books>> getAllBooksAdmin() {
        List<Books> books = booklogic.getAllIncludingUnapproved();
        if(books.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    @GetMapping("/getBook/{id}")
    public ResponseEntity<Books> getMethodName(@PathVariable long id) {
        Books var = booklogic.getBook(id);
        if(var == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(var,HttpStatus.OK);
    }
    
    @GetMapping("/books/author/{author}")
    public ResponseEntity<List<Books>> searchByAuthor(@PathVariable String author) {
        List<Books> books = booklogic.searchByAuthor(author);
        if(books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    @GetMapping("/books/title/{title}")
    public ResponseEntity<List<Books>> searchByTitle(@PathVariable String title) {
        List<Books> books = booklogic.searchByTitle(title);
        if(books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    @GetMapping("/books/price/asc")
    public ResponseEntity<List<Books>> getBooksByPriceAsc() {
        List<Books> books = booklogic.getBooksByPriceAsc();
        if(books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    // @GetMapping("/books/price/desc")
    // public ResponseEntity<List<Books>> getBooksByPriceDesc() {
    //     List<Books> books = booklogic.getBooksByPriceDesc();
    //     if(books.isEmpty()) {
    //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //     }
    //     return new ResponseEntity<>(books, HttpStatus.OK);
    // }
    
}
