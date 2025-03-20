package com.example.bookstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.DTO.LoginForm;
import com.example.bookstore.DTO.Respon;
import com.example.bookstore.Entities.Response;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserController {

    @Autowired
    private UserService userlogic;
    // Signup form 
    @PostMapping("/registoration")
    public ResponseEntity<User> signup(@RequestBody User user) {
        try{
            User usr = userlogic.registor(user);
            if(usr == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The data not store");
            }
            return new ResponseEntity<>(usr,HttpStatus.CREATED);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The Details are not defined");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Response> signin(@RequestBody LoginForm form) {
        // System.out.println(userlogic.getEmail()+" "+userlogic.getPassword());
        String value = userlogic.login(form);
        Response<String> response;
        if (value.equals("Fail")) {
            response = new Response<>(HttpStatus.UNAUTHORIZED.value(),"UnAuthorized",null);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        } 
        else if(value.equals("Empty")){
            response = new Response<>(HttpStatus.NOT_FOUND.value(),"Not Found",null);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        Response<Respon> responses = new Response<>(HttpStatus.ACCEPTED.value(), "Autherized Successfull",new Respon(null, value));
        return new ResponseEntity<>(responses,HttpStatus.ACCEPTED);
    }
    
    
    // Signin form 
    // Get csrf token
    @GetMapping("/getCsrf")
    public CsrfToken getCSRF(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
