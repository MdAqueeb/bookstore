package com.example.bookstore.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.Cart;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Entities.Wishlist;
import com.example.bookstore.Repository.BookRepo;
import com.example.bookstore.Repository.CartRepo;
import com.example.bookstore.Repository.UserRepo;

@Service
public class CartService {

    @Autowired
    private CartRepo cartrepo;

    @Autowired
    private BookRepo bookrepo;

    @Autowired
    private UserRepo userrepo;

    public Cart AddItem(long user, long book) {
        // Retrieve the User and Book from the database
        User usr = userrepo.findById(user)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Books bok = bookrepo.findById(book)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        // Find the user's cart
        Cart cart = cartrepo.findByUserId(user);
        
        if (cart == null) {
            // If the cart doesn't exist, create a new one
            cart = new Cart();
            cart.setUser(usr); // Initialize the books list
        }

        // Add the book to the cart (do not overwrite the previous list)
        List<Books> items = cart.getBooks();
        if (!items.contains(bok)) { // Avoid duplicate entries
            items.add(bok); // Add the book to the cart if it's not already in it
        }

        // Recalculate the cart details
        cart.calculateCartDetails();  // This will update the totalAmount and totalItems
        
        // Save the updated cart
        return cartrepo.save(cart);
    }

    public List<Books> getItemsInCart(long userId) {
        // Find the user's cart by their user ID
        Cart cart = cartrepo.findByUserId(userId);

        // If the cart is not found, return null or an empty list depending on your needs
        if (cart == null) {
            return null;  // or return an empty list if you prefer
        }

        // Return the list of books in the cart
        return cart.getBooks();
    }

}
