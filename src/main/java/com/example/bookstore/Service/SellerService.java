package com.example.bookstore.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.SalesOverview;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Repository.BookRepo;
import com.example.bookstore.Repository.SalesOverView;
// import com.example.bookstore.Repository.BooksRepository;
import com.example.bookstore.Repository.UserRepo;

import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
// import java.util.stream.Collectors;

@Service
public class SellerService {

    @Autowired
    private BookRepo booksRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private SalesOverView repo;

    public Books addBook(Books book, String email) {
        Optional<User> optionalSeller = userRepository.findByEmail(email);
    
        if (optionalSeller.isEmpty()) {
            throw new RuntimeException("User not found");
        }
    
        User seller = optionalSeller.get();
    
        if (seller.getRole() != User.Role.SELLER && seller.getRole() != User.Role.ADMIN) {
            throw new RuntimeException("User is not authorized to sell books");
        }
    
        if (seller.getRole() == User.Role.ADMIN) {
            book.setApproved(Books.Approved.ACCEPTED);
        } else {
            book.setApproved(Books.Approved.PENDING);
        }
    
        book.setSeller(seller);
    
        Books savedBook = booksRepository.save(book);
        System.out.println("Book saved: " + savedBook.getTitle() + " | ID: ");
    
        if (seller.getBookListings() == null) {
            seller.setBookListings(new ArrayList<>());
        }
        seller.getBookListings().add(savedBook);
    
        return savedBook;
    }
    

    public List<Books> getBooksBySellerEmail(String email) {
        User seller = userRepository.findByEmail(email).get();
        if(seller == null){
            throw new RuntimeException("User not found");
        }
        return seller.getBookListings();
    }

    public Books updateBook(long bookId, Books updatedBook, String email) {
        User seller = userRepository.findByEmail(email).get();
        if(seller == null){
            throw new RuntimeException("User not found");
        }

        Books existingBook = seller.getBookListings().stream()
                .filter(b -> b.getBookid() == bookId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not found or not owned by this seller"));
        SalesOverview sales = repo.findByUserid(seller.getUserid());
        if(sales == null){
            sales = new SalesOverview();
            sales.setSeller(existingBook.getSeller());
        }
        HashMap<String,Integer> b = sales.getVar();
        if(b.containsKey(existingBook.getTitle())){
            int value = b.get(existingBook.getTitle());
            b.remove(existingBook.getTitle());
            b.put(updatedBook.getTitle(), value);
        }
        else{
            b.put(updatedBook.getTitle(), 0);
        }
        sales.setVar(b);
        repo.save(sales);

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setDescription(updatedBook.getDescription());
        existingBook.setPrice(updatedBook.getPrice());
        existingBook.setImage(updatedBook.getImage());
        return booksRepository.save(existingBook);
    }

    public void deleteBook(long bookId, String email) {
        User seller = userRepository.findByEmail(email).get();
        if(seller == null){
            throw new RuntimeException("User not found");
        }
        // Check if the book belongs to this seller
        Books bookToRemove = seller.getBookListings().stream()
                .filter(b -> b.getBookid() == bookId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not found or not owned by this seller"));
        
        // Remove the book from seller's listings
        seller.getBookListings().remove(bookToRemove);
        userRepository.save(seller);
        
        // Delete the book
        booksRepository.deleteById(bookId);
    }

    public User getSellerProfile(String email) {
        User seller = userRepository.findByEmail(email).get();
        if(seller == null){
            throw new RuntimeException("User not found");
        }
        return seller;
    }
} 