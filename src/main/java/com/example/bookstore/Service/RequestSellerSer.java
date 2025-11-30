package com.example.bookstore.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.Entities.RequestSellerRole;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Repository.RequestSellerRepo;
import com.example.bookstore.Repository.UserRepo;

@Service
public class RequestSellerSer {

    @Autowired
    private UserRepo user;

    @Autowired
    private RequestSellerRepo requestrole;

    public List<RequestSellerRole> getAllRequest(String email) {

        Optional<User> usr = user.findByEmail(email); 
        
        if(usr.isEmpty()){
            return null;
        }
        System.out.println(usr.get().getName());
        return requestrole.findByUser(usr.get().getUserid());
    }

    public RequestSellerRole addRequest(String email, RequestSellerRole entity) {
        // TODO Auto-generated method stub
        Optional<User> usr = user.findByEmail(email); 
        if(usr.isEmpty()){
            return null;
        }
        System.out.println(usr.get());
        entity.setUserid(usr.get());
        return requestrole.save(entity);
    }
    
}
