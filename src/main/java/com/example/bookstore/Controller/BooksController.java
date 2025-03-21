package com.example.bookstore.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Service.BookService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class BooksController {

    @Autowired
    private BookService booklogic;

    @PostMapping("/AddBooks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Books> postMethodName(@RequestBody Books entity) {
        try{
            Books var = booklogic.AddBook(entity);
            if(var == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The values are not assign to Database");
            }
            return new ResponseEntity<>(var,HttpStatus.CREATED);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"The request values are not assign");
        }
    }

    @GetMapping("/AllBooks")
    public ResponseEntity<List<Books>> getMethodName() {
        List<Books> var = booklogic.getAll();
        if(var.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(var,HttpStatus.OK);
    }
    
    @GetMapping("/getBook/{id}")
    public ResponseEntity<Books> getMethodName(@PathVariable long id) {
        Books var = booklogic.getBook(id);
        if(var == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(var,HttpStatus.OK);
    }
    
}
