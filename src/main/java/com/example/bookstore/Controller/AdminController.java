package com.example.bookstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.Order;
// import com.example.bookstore.Entities.Payment;
import com.example.bookstore.Entities.RequestSellerRole;
import com.example.bookstore.Entities.Response;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin role Endpoints", description = "All Endpoints Only Admin Access")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Book management endpoints
    @GetMapping("/books")
    @Operation(summary = "Get all Books", description = "Returns all books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns List of Books"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Books>> getAllBooks() {
        List<Books> books = adminService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @Operation(summary="Approve Book for sell", description = "Return the Approve book details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns Approved Book details"),
        @ApiResponse(responseCode = "404", description = "The Book id is invalid"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/books/{id}/{approved}")
    public ResponseEntity<Books> approveBook(@PathVariable long id,@PathVariable String approved) {
        try {
            Books book = adminService.approveBook(id,approved);
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found or could not be approved");
        }
    }
    @Operation(summary="Remove Book for sell", description = "Return the Remove book details for sell")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book was removed for sell"),
        @ApiResponse(responseCode = "404", description = "The Book id is invalid or The book already not in sell"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/books/{id}/disapprove")
    public ResponseEntity<Books> rejectBook(@PathVariable long id) {
        try {
            Books book = adminService.disapproveBook(id);
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found or could not be approved");
        }
    }

    @Operation(summary="Delete the book", description = "Return the deleted book Message")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book was deleted"),
        @ApiResponse(responseCode = "404", description = "The Book id is invalid or not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary="All Users details", description = "Return all users in list format")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "list of users"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @Operation(summary="List of users but only seller role", description = "Return the List of sellers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book was deleted"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/sellers")
    public ResponseEntity<List<User>> getAllSellers() {
        List<User> users = adminService.getAllSellers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary="Update role of user", description = "Return the user details after updation of role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role updated of particular user"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/users/{id}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable long id, @RequestParam User.Role role) {
        try {
            User user = adminService.updateUserRole(id, role);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found or role cannot be updated");
        }
    }

    @Operation(summary="Delete particular user", description = "Return the message after deletion")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User was deleted"),
        @ApiResponse(responseCode = "404", description = "The user id is invalid or Already deleted user"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary="Get list of books that are Pending Status", description = "Returns the list of books that are Pending Status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fetch List Successfull"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/books/pending")
    public ResponseEntity<List<Books>> getPendingBooks() {
        List<Books> pendingBooks = adminService.getPendingBooks();
        return ResponseEntity.ok(pendingBooks);
    }

    @Operation(summary="Approve book status", description = "Return book details that is Approved")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book was Approved"),
        @ApiResponse(responseCode = "404", description = "The Book id is invalid or not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/books/{id}/approve-listing")
    public ResponseEntity<Books> approveBookListing(@PathVariable long id) {
        try {
            Books book = adminService.approveBookListing(id);
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found or could not be approved");
        }
    }

    @Operation(summary="Reject the book", description = "Return the reject book details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book was Rejected"),
        @ApiResponse(responseCode = "404", description = "The Book id is invalid or not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary="Get Orders list", description = "Return the List of orders")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of orders"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = adminService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary="Get Particular Order", description = "Return Specific Order details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fetch order details successfull"),
        @ApiResponse(responseCode = "404", description = "The Order id is invalid"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary="Accept user request", description = "Returns accepted user details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request Accepted"),
        @ApiResponse(responseCode = "404", description = "User not found or book not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary="Reject user role request", description = "Return the Rejection")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rejected successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary="Get all requests", description = "Return List of user requests")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All request in list"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary="Change seller to user", description = "Return user details after role update")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Changes Seller to User"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

