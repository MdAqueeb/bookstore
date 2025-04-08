package com.example.bookstore.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Repository.BookRepo;
import com.example.bookstore.Repository.UserRepo;

@Service
public class BookService {

    @Autowired
    private BookRepo bookManipulaiton;
    
    @Autowired
    private UserRepo userRepo;

    public Books AddBook(Books book, String email) {
        System.out.println("To add books It is a  service method");
        // Find the user by email
        User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Set approval status based on user role
        if (user.getRole() == User.Role.ADMIN) {
            book.setApproved(true);
        } else if (user.getRole() == User.Role.SELLER) {
            book.setApproved(false); // Sellers need admin approval
        } else {
            throw new RuntimeException("User is not authorized to add books");
        }

        
        // Set the seller
        book.setSeller(user);

        // Initialize the book listings if null
        if (user.getBookListings() == null) {
            user.setBookListings(new ArrayList<>());
        }
        
        // Save the book first
        Books savedBook = bookManipulaiton.save(book);
        
        // Add the book to seller's listings
        user.getBookListings().add(savedBook);
        userRepo.save(user);
        
        return savedBook;
    }

    public List<Books> getAll() {
        // Only return approved books for public view
        return bookManipulaiton.findByApproved(true);
    }

    public List<Books> getAllIncludingUnapproved() {
        // Return all books, including unapproved ones (for admin use)
        return bookManipulaiton.findAll();
    }
    
    public List<Books> searchByAuthor(String author) {
        // Search for books by author (only approved books)
        List<Books> books = bookManipulaiton.findByAuthorContainingIgnoreCase(author);
        return books.stream()
                .filter(book -> book.getApproved() != null && book.getApproved())
                .collect(java.util.stream.Collectors.toList());
    }
    
    public List<Books> searchByTitle(String title) {
        // Search for books by title (only approved books)
        List<Books> books = bookManipulaiton.findByTitleContainingIgnoreCase(title);
        return books.stream()
                .filter(book -> book.getApproved() != null && book.getApproved())
                .collect(java.util.stream.Collectors.toList());
    }

    public List<Books> getBooksByPriceAsc() {
        // Get approved books sorted by price (ascending)
        return bookManipulaiton.findByApprovedOrderByPriceAsc(true);
    }
    
    // public List<Books> getBooksByPriceDesc() {
    //     // Get approved books sorted by price (descending)
    //     return bookManipulaiton.findByApprovedOrderByPriceDesc(true);
    // }

    public Books getBook(long id) {
        Optional<Books> book = bookManipulaiton.findById(id);
        if (book.isPresent()) {
            Books foundBook = book.get();
            // Only return if approved or admin is accessing
            if (foundBook.getApproved() != null && foundBook.getApproved()) {
                return foundBook;
            }
            // If not approved, will return null, which the controller will handle as not found
        }
        return null;
    }

    

    public List<Books> AddlistBook(List<Books> entity, String email) {
        // TODO Auto-generated method stub

        System.out.println("To add books It is a  service method");
        // Find the user by email
        User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Set approval status based on user role
        List<Books> savedBook = new ArrayList<>();
        for(int i = 0;i < entity.size();i++){
            if (user.getRole() == User.Role.ADMIN) {
                entity.get(i).setApproved(true);
            } else if (user.getRole() == User.Role.SELLER) {
                entity.get(i).setApproved(false); // Sellers need admin approval
            } else {
                throw new RuntimeException("User is not authorized to add books");
            }
    
            
            // Set the seller
            entity.get(i).setSeller(user);
    
            // Initialize the book listings if null
            if (user.getBookListings() == null) {
                user.setBookListings(new ArrayList<>());
            }
            
            // Save the book first
            savedBook.add(bookManipulaiton.save(entity.get(i)));
            
            // Add the book to seller's listings
            user.getBookListings().add(savedBook.get(i));
            userRepo.save(user);
        }
        
        
        return savedBook;
        // throw new UnsupportedOperationException("Unimplemented method 'AddlistBook'");
    }
}
