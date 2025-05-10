package com.example.bookstore.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.Entities.Order;
import com.example.bookstore.Entities.SalesOverview;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Repository.SalesOverView;
import com.example.bookstore.Repository.UserRepo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class SalesController {
    
    @Autowired 
    private SalesOverView repo;

    @Autowired
    private UserRepo usrrepo;

    // @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/GetSales")
    public ResponseEntity<?> getMethodName() {
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Wait getting Order");
            String email = auth.getName();
            System.out.println(email);
            Optional<User> usr = usrrepo.findByEmail(email);
            System.out.println(usr.isPresent());
            if(!usr.isPresent()){
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
            SalesOverview sles = repo.findByUserid(usr.get().getUserid());
            System.out.println(sles);
            if(sles == null){
                sles = new SalesOverview();
                sles.setSeller(usr.get());
                repo.save(sles);
            }

            return new ResponseEntity<>(sles,HttpStatus.OK);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    
}
