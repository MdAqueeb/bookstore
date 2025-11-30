package com.example.bookstore.Controller;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

// import com.example.bookstore.Entities.Order;
import com.example.bookstore.Entities.PurchasedBooks;
import com.example.bookstore.Service.MyBookService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Tag(name = "Purchaced Books EndPoints", description = "Buyed Books Endpoints")
public class MyBooksController {
    
    @Autowired
    private MyBookService mybook;

    @GetMapping("/getPurchasedBooks")
    public ResponseEntity<?> getMethodName() {
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            PurchasedBooks books = mybook.GetPurchaseBooks(auth.getName());
            if(books == null){
                return new ResponseEntity<>(books,HttpStatus.CONFLICT);
            } 
            return new ResponseEntity<>(books,HttpStatus.OK);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    
}
