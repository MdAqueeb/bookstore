package com.example.bookstore.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.Order;
import com.example.bookstore.Entities.Payment;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Repository.BookRepo;
// import com.example.bookstore.Repository.BooksRepository;
import com.example.bookstore.Repository.OrderRepository;
import com.example.bookstore.Repository.PaymentRepository;
import com.example.bookstore.Repository.UserRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private BookRepo booksRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    // Book management methods
    public List<Books> getAllBooks() {
        return booksRepository.findAll();
    }

    public Books approveBook(long id) {
        Optional<Books> bookOpt = booksRepository.findById(id);
        if (bookOpt.isPresent()) {
            Books book = bookOpt.get();
            book.setApproved(true);
            // Set approval status or other properties if needed
            return booksRepository.save(book);
        }
        throw new RuntimeException("Book not found");
    }

    public void deleteBook(long id) {
        if (booksRepository.existsById(id)) {
            booksRepository.deleteById(id);
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    // User management methods
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUserRole(long id, User.Role role) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setRole(role);
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    public void deleteUser(long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Order management methods
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // Payment management methods
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    // Dashboard statistics
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Count of total books
        stats.put("totalBooks", booksRepository.count());
        
        // Count of total users
        stats.put("totalUsers", userRepository.count());
        
        // Count of total orders
        stats.put("totalOrders", orderRepository.count());
        
        // Count of total payments
        stats.put("totalPayments", paymentRepository.count());
        
        // Add more statistics as needed
        
        return stats;
    }

    // Book approval methods
    public List<Books> getPendingBooks() {
        // Get all books that haven't been approved yet
        List<Books> allBooks = booksRepository.findAll();
        return allBooks.stream()
                .filter(book -> book.getApproved() == null || !book.getApproved())
                .collect(java.util.stream.Collectors.toList());
    }

    public Books approveBookListing(long id) {
        Optional<Books> bookOpt = booksRepository.findById(id);
        if (bookOpt.isPresent()) {
            Books book = bookOpt.get();
            book.setApproved(true);
            return booksRepository.save(book);
        }
        throw new RuntimeException("Book not found");
    }

    public Books rejectBookListing(long id) {
        Optional<Books> bookOpt = booksRepository.findById(id);
        if (bookOpt.isPresent()) {
            Books book = bookOpt.get();
            book.setApproved(false);
            return booksRepository.save(book);
        }
        throw new RuntimeException("Book not found");
    }
} 