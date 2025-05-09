package com.example.bookstore.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.Cart;
// import com.example.bookstore.Entities.Wishlist;
import com.example.bookstore.Service.CartService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController

public class CartController {

    @Autowired
    private CartService cartserve;

    @PostMapping("AddCartItem/{bookid}")
    public ResponseEntity<Cart> putMethodName(@PathVariable long bookid) {
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(auth.getName());
            String email = auth.getName(); 
            Cart val = cartserve.AddItem(email,bookid);

            if(val == null){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"The item not added to Cart");
            }
            return new ResponseEntity<>(val,HttpStatus.CREATED);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    @GetMapping("/GetCartItems")
    public ResponseEntity<List<Books>> getCartItems() {
    
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(auth.getName());
            String email = auth.getName(); 
            List<Books> books = cartserve.getItemsInCart(email);
            System.out.println(books);
            if (books.isEmpty()) {
                return new ResponseEntity<>(books,HttpStatus.NO_CONTENT);  
            }
            return new ResponseEntity<>(books,HttpStatus.OK);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @DeleteMapping("RemoveCartItem/{bookid}")
    public ResponseEntity<String> deleteCartBook(@PathVariable long bookid){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(auth.getName());
            String email = auth.getName(); 
            String val = cartserve.removeCartBook(email,bookid);

            if(val.equals("User Not Found") || val.equals("Cart not found") || val.equals("Book not found in cart")){
                return new ResponseEntity<>(val,HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(val,HttpStatus.OK);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

}
