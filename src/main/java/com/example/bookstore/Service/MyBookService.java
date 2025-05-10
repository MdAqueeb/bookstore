package com.example.bookstore.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.Order;
import com.example.bookstore.Entities.PurchasedBooks;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Repository.MyBooksRep;
import com.example.bookstore.Repository.UserRepo;

@Service
public class MyBookService {
    
    @Autowired
    private MyBooksRep myrepo;

    @Autowired
    private UserRepo user;

    public List<PurchasedBooks> GetPurchaseBooks(String email) {
        Optional<User> usr = user.findByEmail(email);
        if(!usr.isPresent()){
            return null;
        }

        List<PurchasedBooks> books = myrepo.findByUserId(usr.get().getUserid());
        return books;
    }

    public PurchasedBooks AddBooks(Order order) {
        User usr = order.getUser();
        List<Books> books = new ArrayList<>();
        if(order.getBook() == null && order.getCart() != null){
            books.addAll(order.getCart().getBooks());
        }
        else if(order.getBook() != null){
            books.add(order.getBook());
        }
        PurchasedBooks boks = new PurchasedBooks();
        boks.setOrderdate(order.getOrderDate());
        boks.setBook(books);
        boks.setUser(usr);
        return myrepo.save(boks);

    }


}
