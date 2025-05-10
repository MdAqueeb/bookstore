package com.example.bookstore.Entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class PurchasedBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "userid", unique = true) 
    private User user;

    @ToString.Exclude
    @OneToMany(mappedBy = "purchasedBooks")
    private List<Books> book;

    @Column(nullable =  false)
    private LocalDateTime orderdate;
}
