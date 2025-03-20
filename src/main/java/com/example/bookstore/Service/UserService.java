package com.example.bookstore.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


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
        User user = usertable.findByEmail(form.getEmail());
        Authentication authentication;
        if(user != null && bcrypt.matches(form.getPassword(), user.getPassword())){
            System.out.println("Hello");
            authentication = authmanager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), form.getPassword())); 
            if(authentication.isAuthenticated()){
                System.out.println("Yes Comes");
                return jwtservice.generate_Token(user.getName());
            }
        }
        if(user == null){
            return "Empty";
        }
        return "Fail";
    }
}
