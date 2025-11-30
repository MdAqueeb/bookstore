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

    public PurchasedBooks GetPurchaseBooks(String email) {
        Optional<User> usr = user.findByEmail(email);
        if(usr.isEmpty()){
            return null;
        }

        PurchasedBooks crt = myrepo.findByUserId(usr.get().getUserid());
        System.out.println(crt);
        if(crt == null){
            crt = new PurchasedBooks();
            crt.setUser(usr.get());;
            return myrepo.save(crt);
        }
        return crt;
    }

    public PurchasedBooks AddBooks(Order order) {
        User usr = order.getUser();
        PurchasedBooks boks = myrepo.findByUserId(usr.getUserid());

        // Create new PurchasedBooks if not exists
        if (boks == null) {
            boks = new PurchasedBooks();
            boks.setUser(usr);
            boks = myrepo.save(boks); 
        }

        List<Books> booksToAdd = new ArrayList<>();

        // Determine books from order or cart
        if (order.getBook() == null && order.getCart() != null) {
            booksToAdd.addAll(order.getCart().getBooks());
        } else if (order.getBook() != null) {
            System.out.println("We added The Book");
            booksToAdd.add(order.getBook());
        }

        // Avoid duplicate books
        for (Books book : booksToAdd) {
            if (!boks.getBooks().contains(book)) {
                boks.getBooks().add(book);
            }
        }

        return myrepo.save(boks);
    }



}
