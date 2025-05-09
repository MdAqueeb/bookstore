package com.example.bookstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.bookstore.DTO.ForgotPassword;
import com.example.bookstore.DTO.LoginForm;
import com.example.bookstore.DTO.Respon;
import com.example.bookstore.Entities.Response;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Repository.UserRepo;
import com.example.bookstore.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class UserController {

    @Autowired
    private UserService userlogic;

    @Autowired
    private UserRepo userrepo;
    // Signup form 
    @PostMapping("/registoration")
    public ResponseEntity<User> signup(@RequestBody User user) {
        try{
            User usr = userlogic.registor(user);
            
            if(usr == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The data not store");
            }
            System.out.println(usr.getAvator());
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
        if (value.equals("Invalid password")) {
            response = new Response<>(HttpStatus.UNAUTHORIZED.value(),"UnAuthorized",null);
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        } 
        else if(value.equals("Login failed")){
            response = new Response<>(HttpStatus.NOT_FOUND.value(),"Not Found",null);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

        User user = userrepo.findByEmail(form.getEmail()).get();
        Response<Respon> responses = new Response<>(HttpStatus.ACCEPTED.value(), "Autherized Successfull",new Respon(user, value));
        return new ResponseEntity<>(responses,HttpStatus.ACCEPTED);
    }
    
    
    // Signin form 
    // Get csrf token
    @GetMapping("/getCsrf")
    public CsrfToken getCSRF(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }
    
    // Request to become a seller

    @PostMapping("/user/become-seller")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response> requestSellerRole() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(auth.getName());
            String email = auth.getName(); // This will now be the email since we changed Userprinciples
            System.out.println(email);
            User member = userlogic.requestSellerRole(email);
            Response<User> response = new Response<>(HttpStatus.OK.value(), "Seller role request submitted successfully", member);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/getProfile")
    public ResponseEntity<User> getUserProfile() {
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(auth.getName());
            String email = auth.getName(); 
            User usr = userlogic.getUser(email);

            if(usr == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(usr,HttpStatus.OK);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @PostMapping("/VerifyEmail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email) {
        User usr = userlogic.Verify(email);
        if(usr == null){
            return new ResponseEntity<>("There is no User",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("The user Found",HttpStatus.OK);
    }

    @PutMapping("/ForgotPassword")
    public ResponseEntity<User> putMethodName(@RequestBody ForgotPassword entity) {
        User usr = userlogic.UpdatePassword(entity);
        if(usr == null){
            return new ResponseEntity<>(usr,HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(usr,HttpStatus.ACCEPTED);
    }
    
    
}
