package com.example.bookstore.Entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

// import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "books")
// @ToString
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookid;

    @Column(nullable =  false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal price;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Approved approved ;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "sellerid", nullable = false) // Link book to seller
    // @JsonIgnore
    private User seller; 
    

    // why i use all means i not need to store in books table
    @ToString.Exclude
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Order order;

    // see any missing 
    @ToString.Exclude
    @ManyToMany(mappedBy = "books")
    @JsonIgnore
    private List<Cart> cart = new ArrayList<>();

    public enum Approved{
        PENDING,REJECTED,ACCEPTED;
    }

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private List<Wishlist> wishlists = new ArrayList<>();

    @ManyToMany(mappedBy = "books")
    @ToString.Exclude
    @JsonBackReference
    private List<PurchasedBooks> purchaseRecords = new ArrayList<>();
}