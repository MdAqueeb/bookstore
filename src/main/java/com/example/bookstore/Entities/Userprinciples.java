package com.example.bookstore.Entities;


import java.util.Collection;
import java.util.Collections;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class Userprinciples implements UserDetails{

    private User user;
    public Userprinciples(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return user.getPassword();
       
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return user.getName();

        
    }
    
}
