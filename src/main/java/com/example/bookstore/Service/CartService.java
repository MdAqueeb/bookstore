package com.example.bookstore.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.Cart;
import com.example.bookstore.Entities.User;
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

    public Cart AddItem(String email,long book) {
        // Retrieve the User and Book from the database
        User usr = userrepo.findByEmail(email)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Books bok = bookrepo.findById(book)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        // Find the user's cart
        if(usr == null || bok == null){
            return null;
        }

        Optional<Cart> cart = cartrepo.findByUserId(usr.getUserid());

        Cart cartToSave;
        if (!cart.isPresent()) {
            // If the cart doesn't exist, create a new one
            cartToSave = new Cart();
            cartToSave.setUser(usr);
            cartToSave.setTotalAmount(BigDecimal.ZERO);
            cartToSave.setTotalItems(0);
        } else {
            cartToSave = cart.get();
        }

        // Add the book to the cart (do not overwrite the previous list)
        List<Books> items = cartToSave.getBooks();
        if (!items.contains(bok)) { // Avoid duplicate entries
            items.add(bok); // Add the book to the cart if it's not already in it
        }
        
        // Recalculate the cart details
        cartToSave.calculateCartDetails();  // This will update the totalAmount and totalItems
        
        // Save the updated cart
        return cartrepo.save(cartToSave);
    }

    public List<Books> getItemsInCart(String email) {
        // Find the user's cart by their user ID
        Optional<User> usr = userrepo.findByEmail(email);
        if(!usr.isPresent()){
            // System.out.println(usr.get());
            return new ArrayList<>();
        }
        System.out.println(usr.get());
        Optional<Cart> cart = cartrepo.findByUserId(usr.get().getUserid());


        if(!cart.isPresent()) { 
            return new ArrayList<>();
        }

        return cart.get().getBooks();
    }

    public String removeCartBook(String email, long bookid) {
        // Retrieve user by email
        User usr = userrepo.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        // Retrieve user's cart
        Cart cart = cartrepo.findByUserId(usr.getUserid())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
    
        // Iterate through the books in the cart using an Iterator for safe removal
        Iterator<Books> iterator = cart.getBooks().iterator();
        String res = "Book not found in cart";
        while (iterator.hasNext()) {
            Books book = iterator.next();
            if (book.getBookid() == bookid) {
                iterator.remove();  // Safely remove the book from the cart
                res = "Book Found";
                break;
            }
        }
        cartrepo.save(cart);
        return res;
    }
    

}
