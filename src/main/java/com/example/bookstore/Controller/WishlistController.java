package com.example.bookstore.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.Wishlist;
import com.example.bookstore.Service.WishlistService;
// import org.springframework.web.bind.annotation.PutMapping;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@Tag(name = "Wishlist Endpoints", description = "All Wishlist Endpoints here")
public class WishlistController {

    @Autowired
    private WishlistService wishlistServ;

    @PostMapping("AddWishlist/{bookid}")
    public ResponseEntity<Wishlist> putMethodName(@PathVariable long bookid) {
        

        
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(auth.getName());
            String email = auth.getName(); 
            Wishlist val = wishlistServ.AddWishlist(email,bookid);
            if(val == null){
                return new ResponseEntity<>(val,HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(val,HttpStatus.OK);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/GetWishlist")
    public ResponseEntity<List<Books>> getMethodName() {
        
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(auth.getName());
            String email = auth.getName(); 
            List<Books> val = wishlistServ.GetAll(email);
            if(val.isEmpty()){
                return new ResponseEntity<>(val,HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(val,HttpStatus.OK);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
    @DeleteMapping("/RemoveWishlist/{id}")
    public ResponseEntity<String> deleteWishlistBook(@PathVariable long id){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(auth.getName());
            String email = auth.getName(); 
            String val = wishlistServ.RemoveBook(email,id);

            if(val.equals("User Not Found") || val.equals("Book Not Found")){
                return new ResponseEntity<>(val,HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(val,HttpStatus.ACCEPTED);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

}
