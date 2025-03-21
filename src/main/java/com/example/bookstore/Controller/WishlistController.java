package com.example.bookstore.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.Entities.Wishlist;
import com.example.bookstore.Service.WishlistService;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class WishlistController {

    @Autowired
    private WishlistService wishlistServ;

    @PostMapping("AddWishlist/{userid}/{bookid}")
    public ResponseEntity<Wishlist> putMethodName(@PathVariable long userid, @PathVariable long bookid) {
        Wishlist val = wishlistServ.AddWishlist(userid,bookid);
        if(val == null){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"The item not added to wishlist");
        }
        return new ResponseEntity<>(val,HttpStatus.ACCEPTED);
    }

    @GetMapping("/GetWishlist")
    public ResponseEntity<List<Wishlist>> getMethodName() {
        List<Wishlist> val = wishlistServ.GetAll();
        if(val.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"There is No items");
        }
        return new ResponseEntity<>(val,HttpStatus.OK);

    }
    


}
