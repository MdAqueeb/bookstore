package com.example.bookstore.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.Cart;
import com.example.bookstore.Entities.Wishlist;
import com.example.bookstore.Service.CartService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class CartController {

    @Autowired
    private CartService cartserve;

    @PostMapping("AddItem/{userid}/{bookid}")
    public ResponseEntity<Cart> putMethodName(@PathVariable long userid, @PathVariable long bookid) {
        Cart val = cartserve.AddItem(userid,bookid);
        if(val == null){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"The item not added to wishlist");
        }
        return new ResponseEntity<>(val,HttpStatus.CREATED);
    }
    
    @GetMapping("/cart/{userId}")
    public ResponseEntity<List<Books>> getCartItems(@PathVariable long userId) {
        List<Books> books = cartserve.getItemsInCart(userId);

        if (books == null || books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // If no items found
        }

        return new ResponseEntity<>(books, HttpStatus.OK);  // Return the list of books in the cart
    }

}
