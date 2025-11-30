package com.example.bookstore.Service;

import java.util.ArrayList;
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

    public Wishlist AddWishlist(String user, long book) {
        Wishlist obj = new Wishlist();

        Optional<User> usr = userrepo.findByEmail(user);
        Optional<Books> bok = bookrepo.findById(book);
        
        if(usr.isEmpty() || bok.isEmpty()){
            return null;
        }
        
        obj.setBook(bok.get());
        obj.setUser(usr.get());
        List<Wishlist> identify = wishlistrepo.findByUserBook(usr.get().getUserid(), book);

        if(!identify.isEmpty()){
            return null;
        }
        


        return wishlistrepo.save(obj);

    }

    public List<Books> GetAll(String email) {

        Optional<User> usr = userrepo.findByEmail(email);
        if(usr.isEmpty()){
            return null;
        }
        List<Wishlist> val = wishlistrepo.findByUserId(usr.get().getUserid());
        List<Books> book = new ArrayList<>();
        for(int i = 0;i < val.size();i++){
            Books bok = val.get(i).getBook();
            System.out.println(val.get(i).getBook());
            book.add(bok);

        }
        // System.out.println(book);
        return book;
    }

    public String RemoveBook(String email,long bookid) {
        // TODO Auto-generated method stub
        System.out.println("It is in RemoveBook method");
        Optional<User> usr = userrepo.findByEmail(email);
        Optional<Books> book = bookrepo.findById(bookid);
        if(usr.isEmpty()){
            return "User Not Found";
        }
        if(book.isEmpty()){
            return "Book Not Found";
        }
        wishlistrepo.RemoveByEmailAndId(usr.get().getUserid(),bookid);
        return "Deleted Successfull";
    }   
    
}
