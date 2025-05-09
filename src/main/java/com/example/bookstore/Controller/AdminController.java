package com.example.bookstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.Order;
// import com.example.bookstore.Entities.Payment;
import com.example.bookstore.Entities.RequestSellerRole;
import com.example.bookstore.Entities.Response;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Service.AdminService;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Book management endpoints
    @GetMapping("/books")
    public ResponseEntity<List<Books>> getAllBooks() {
        List<Books> books = adminService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @PutMapping("/books/{id}/{approved}")
    public ResponseEntity<Books> approveBook(@PathVariable long id,@PathVariable String approved) {
        try {
            Books book = adminService.approveBook(id,approved);
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found or could not be approved");
        }
    }

    @PutMapping("/books/{id}/disapprove")
    public ResponseEntity<Books> rejectBook(@PathVariable long id) {
        try {
            Books book = adminService.disapproveBook(id);
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found or could not be approved");
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Response> deleteBook(@PathVariable long id) {
        try {
            adminService.deleteBook(id);
            Response<String> response = new Response<>(HttpStatus.OK.value(), "Book deleted successfully", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
    }

    // User management endpoints
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/sellers")
    public ResponseEntity<List<User>> getAllSellers() {
        List<User> users = adminService.getAllSellers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable long id, @RequestParam User.Role role) {
        try {
            User user = adminService.updateUserRole(id, role);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found or role cannot be updated");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable long id) {
        try {
            adminService.deleteUser(id);
            Response<String> response = new Response<>(HttpStatus.OK.value(), "User deleted successfully", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    // Book approval endpoints (for seller listings)
    @GetMapping("/books/pending")
    public ResponseEntity<List<Books>> getPendingBooks() {
        List<Books> pendingBooks = adminService.getPendingBooks();
        return ResponseEntity.ok(pendingBooks);
    }

    @PutMapping("/books/{id}/approve-listing")
    public ResponseEntity<Books> approveBookListing(@PathVariable long id) {
        try {
            Books book = adminService.approveBookListing(id);
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found or could not be approved");
        }
    }

    @PutMapping("/books/{id}/reject")
    public ResponseEntity<Books> rejectBookListing(@PathVariable long id) {
        try {
            Books book = adminService.rejectBookListing(id);
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found or could not be rejected");
        }
    }

    // Order management endpoints
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = adminService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable long id) {
        try {
            Order order = adminService.getOrderById(id);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
    }

    // Payment management endpoints
    // @GetMapping("/payments")
    // public ResponseEntity<List<Payment>> getAllPayments() {
    //     List<Payment> payments = adminService.getAllPayments();
    //     return ResponseEntity.ok(payments);
    // }

    // @GetMapping("/payments/{id}")
    // public ResponseEntity<Payment> getPaymentById(@PathVariable long id) {
    //     try {
    //         Payment payment = adminService.getPaymentById(id);
    //         return ResponseEntity.ok(payment);
    //     } catch (Exception e) {
    //         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found");
    //     }
    // }

    // Dashboard statistics
    // @GetMapping("/dashboard")
    // public ResponseEntity<Response> getDashboardStats() {
    //     try {
    //         Object stats = adminService.getDashboardStats();
    //         Response<Object> response = new Response<>(HttpStatus.OK.value(), "Dashboard statistics", stats);
    //         return ResponseEntity.ok(response);
    //     } catch (Exception e) {
    //         throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving dashboard statistics");
    //     }
    // }

    @PutMapping("AcceptRole/{id}")
    public ResponseEntity<User> AcceptRequestRole(@PathVariable long id) {
       
        try {
            User accepted = adminService.ChangeRole(id);
            if(accepted == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
            }
            return new ResponseEntity<>(accepted,HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        
    }

    @PutMapping("RejectRole/{id}")
    public ResponseEntity<List<RequestSellerRole>> putMethodName(@PathVariable long id) {

        try{
            List<RequestSellerRole> reject = adminService.RejectRole(id);
            if(reject == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(reject,HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
    }

    @GetMapping("/GetWholeRequest")
    public ResponseEntity<List<RequestSellerRole>> getRequest() {
        try {

            List<RequestSellerRole> val = adminService.GetWholeRequest();

            return new ResponseEntity<>(val, HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error adding book: " + e.getMessage());
        }
    }

    @PutMapping("ChangetoUser/{id}")
    public ResponseEntity<User> ChangetoUser(@PathVariable long id) {
        try {

            User val = adminService.ChangeSellerRole(id);

            return new ResponseEntity<>(val, HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error adding book: " + e.getMessage());
        }
    }

} 

