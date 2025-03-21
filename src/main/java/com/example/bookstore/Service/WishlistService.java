package com.example.bookstore.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Entities.Wishlist;
import com.example.bookstore.Repository.BookRepo;
import com.example.bookstore.Repository.UserRepo;
import com.example.bookstore.Repository.WishlistRepo;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepo wishlistrepo;

    @Autowired
    private UserRepo userrepo;

    @Autowired
    private BookRepo bookrepo;

    public Wishlist AddWishlist(long user, long book) {
        Wishlist obj = new Wishlist();

        Optional<User> usr = userrepo.findById(user);
        Optional<Books> bok = bookrepo.findById(book);
        
        if(!usr.isPresent() || !bok.isPresent()){
            return null;
        }

        obj.setBooks(bok.get());
        obj.setUser(usr.get());
        
        return wishlistrepo.save(obj);

    }

    public List<Wishlist> GetAll() {
        List<Wishlist> val = wishlistrepo.findAll();
        return val;
    }   
    
}
