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
public class UserdetailService implements UserDetailsService{

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // TODO Auto-generated method stub
        User user1 = repo.findByUserName(username);
        if(user1 == null){
            System.out.println("Not found");
            throw new UsernameNotFoundException("User Not found");
        }
        return new Userprinciples(user1);
        // throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }
    
}