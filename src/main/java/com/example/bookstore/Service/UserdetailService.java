package com.example.bookstore.Service;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bookstore.Entities.User;
import com.example.bookstore.Entities.Userprinciples;
import com.example.bookstore.Repository.UserRepo;

// import jwtauthentication.application.Entities.Userprinciples;

@Service
public class UserdetailService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findByEmail(email).get();
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new Userprinciples(user);
    }
}