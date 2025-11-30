package com.example.bookstore.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import com.example.bookstore.Configuration.AppConfig;
import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.Order;
// import com.example.bookstore.Entities.Payment;
import com.example.bookstore.Entities.RequestSellerRole;
import com.example.bookstore.Entities.User;
// import com.example.bookstore.Entities.User.Role;
import com.example.bookstore.Repository.BookRepo;
// import com.example.bookstore.Repository.BooksRepository;
import com.example.bookstore.Repository.OrderRepository;
// import com.example.bookstore.Repository.PaymentRepository;
import com.example.bookstore.Repository.RequestSellerRepo;
import com.example.bookstore.Repository.UserRepo;

// import java.lang.classfile.ClassFile.Option;
// import java.util.HashMap;
import java.util.List;
// import java.util.Map;
import java.util.Optional;

@Service
public class AdminService {

    // private final Configuration.AppConfig appConfig;

    @Autowired
    private BookRepo booksRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private OrderRepository orderRepository;

    // @Autowired
    // private PaymentRepository paymentRepository;

    @Autowired
    private RequestSellerRepo requestsellerrepo;


    // AdminService(Configuration.AppConfig appConfig) {
    //     this.appConfig = appConfig;
    // }


    // Book management methods
    public List<Books> getAllBooks() {
        return booksRepository.findAll();
    }

    public Books approveBook(long id, String approved) {
        Optional<Books> bookOpt = booksRepository.findById(id);
        if (bookOpt.isPresent()) {
            Books book = bookOpt.get();
            if(approved.equals("ACCEPTED")){
                book.setApproved(Books.Approved.ACCEPTED);
                return booksRepository.save(book);
            }
            else if(approved.equals("REJECTED")){
                book.setApproved(Books.Approved.REJECTED);
                return booksRepository.save(book);
            }
            else if(approved.equals("PENDING")){
                book.setApproved(Books.Approved.PENDING);
                return booksRepository.save(book);
            }
            // Set approval status or other properties if needed
            
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
        return userRepository.findAllRoles();
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

    // // Payment management methods
    // public List<Payment> getAllPayments() {
    //     return paymentRepository.findAll();
    // }

    // public Payment getPaymentById(long id) {
    //     return paymentRepository.findById(id)
    //             .orElseThrow(() -> new RuntimeException("Payment not found"));
    // }

    // Dashboard statistics
    // public Map<String, Object> getDashboardStats() {
    //     Map<String, Object> stats = new HashMap<>();
        
    //     // Count of total books
    //     stats.put("totalBooks", booksRepository.count());
        
    //     // Count of total users
    //     stats.put("totalUsers", userRepository.count());
        
    //     // Count of total orders
    //     stats.put("totalOrders", orderRepository.count());
        
    //     // Count of total payments
    //     stats.put("totalPayments", paymentRepository.count());
        
    //     // Add more statistics as needed
        
    //     return stats;
    // }

    // Book approval methods
    public List<Books> getPendingBooks() {
        // Get all books that haven't been approved yet
        List<Books> allBooks = booksRepository.findByPendingBooks(Books.Approved.PENDING.name());
        return allBooks;
    }

    public Books approveBookListing(long id) {
        Optional<Books> bookOpt = booksRepository.findById(id);
        if (bookOpt.isPresent()) {
            Books book = bookOpt.get();
            book.setApproved(Books.Approved.ACCEPTED);
            return booksRepository.save(book);
        }
        throw new RuntimeException("Book not found");
    }

    public Books rejectBookListing(long id) {
        Optional<Books> bookOpt = booksRepository.findById(id);
        if (bookOpt.isPresent()) {
            Books book = bookOpt.get();
            book.setApproved(Books.Approved.REJECTED);
            return booksRepository.save(book);
        }
        throw new RuntimeException("Book not found");
    }

    public User ChangeRole(long id) {
        Optional<User> usr = userRepository.findById(id);
        if(usr.isEmpty()){
            return null;
        }

        usr.get().getRequestSellerRole().clear();
        usr.get().setRole(User.Role.SELLER);

        
        return userRepository.save(usr.get());
    }

    public List<RequestSellerRole> RejectRole(long id) {

        List<RequestSellerRole> request = requestsellerrepo.findByUser(id);

        if(request.isEmpty()){
            return null;
        }
        for(int i = 0;i < request.size();i++){
            request.get(i).setStatus(RequestSellerRole.Status.REJECTED);
            requestsellerrepo.save(request.get(i));
        }
        
        return request;
        
    }

    public List<RequestSellerRole> GetWholeRequest() {
        // TODO Auto-generated method stub
        return requestsellerrepo.findAll();
    }

    public List<User> getAllSellers() {
        // TODO Auto-generated method stub
        Optional<List<User>> seller = userRepository.findByRole(User.Role.SELLER);

        return seller.get();
    }

    public User ChangeSellerRole(long id) {
        
        Optional<User> seller = userRepository.findById(id);

        if(seller.isEmpty()){
            return null;
        }

        seller.get().setRole(User.Role.USER);

        return userRepository.save(seller.get());
    }

    public Books disapproveBook(long id) {
        Optional<Books> bookOpt = booksRepository.findById(id);
        if (bookOpt.isPresent()) {
            Books book = bookOpt.get();
            book.setApproved(Books.Approved.REJECTED);
            // Set approval status or other properties if needed
            return booksRepository.save(book);
        }
        throw new RuntimeException("Book not found");
    }

    // public Books approveBook(long id, String approved) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'approveBook'");
    // }




} 