package com.example.bookstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.Entities.RequestSellerRole;
import com.example.bookstore.Service.RequestSellerSer;

import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@PreAuthorize("hasRole('USER','ADMIN')")
public class RequestSellerCon {

    @Autowired
    private RequestSellerSer request ;
    
    @GetMapping("/GetAllRequest")
    public ResponseEntity<List<RequestSellerRole>> getRequest() {
        try {
            // Get the authenticated user's email
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            
            // Add the book with the seller's email
            System.out.println("It is in the way");
            List<RequestSellerRole> val = request.getAllRequest(email);
            if(val == null) {
                return new ResponseEntity<>(val,HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(val, HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error adding book: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/AddRequest")
    public ResponseEntity<RequestSellerRole> AddingRequest(@RequestBody RequestSellerRole entity) {
        try {
            // Get the authenticated user's email
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            
            // Add the book with the seller's email
            RequestSellerRole val = request.addRequest(email,entity);
            if(val == null) {
                throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "The Request is Empty");
            }
            return new ResponseEntity<>(val, HttpStatus.ACCEPTED);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error adding book: " + e.getMessage());
        }
    }
    
    
    
    
}
