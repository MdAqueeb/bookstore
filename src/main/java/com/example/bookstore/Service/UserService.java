package com.example.bookstore.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.bookstore.DTO.LoginForm;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Repository.UserRepo;

@Service
public class UserService {
    
    @Autowired
    private UserRepo usertable;

    private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    @Autowired
    @Lazy
    private AuthenticationManager authmanager;

    @Autowired
    private JWTService jwtservice; 

    public User registor(User user){
        if(user.getPassword() == null || user.getPassword().isEmpty()){
            return null;
        }
        user.setPassword(bcrypt.encode(user.getPassword()));
        return usertable.save(user);
    }

    public String login(LoginForm form){
        try {
            Optional<User> user = usertable.findByEmail(form.getEmail());
            
            if(!user.isPresent()){
                return "Login failed";
            }
            User usr = user.get();
            if (bcrypt.matches(form.getPassword(), usr.getPassword())) {
                Authentication authentication = authmanager.authenticate(
                    new UsernamePasswordAuthenticationToken(usr.getEmail(), form.getPassword())
                );
                
                if (authentication.isAuthenticated()) {
                    return jwtservice.generate_Token(usr.getName(), usr.getEmail());
                }
            }
            return "Invalid password";
        } catch (Exception e) {
            return "Login failed";
        }
    }
    
    public User requestSellerRole(String email) {
        User user = usertable.findByEmail(email).get();
        if (user != null) {
            user.setRole(User.Role.SELLER);
            return usertable.save(user);
        }
        return null;
    }

    public User getUser(String email) {
        Optional<User> user = usertable.findByEmail(email);
        if(!user.isPresent()){
            return null;
        }
        return user.get();
        // TODO Auto-generated method stub
    }
}
