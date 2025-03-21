package com.example.bookstore.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Repository.BookRepo;

@Service
public class BookService {

    @Autowired
    private BookRepo bookManipulaiton;

    public Books AddBook(Books book ){
        return bookManipulaiton.save(book);
    }

    public List<Books> getAll() {
        return bookManipulaiton.findAll();
    }

    public Books getBook(long id) {
        Optional<Books> book = bookManipulaiton.findById(id);
        return book.get();
    }

}
