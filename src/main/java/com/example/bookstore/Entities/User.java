package com.example.bookstore.Entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor  
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String address;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String avator ;


    @Column(nullable = false,precision = 10,scale = 2)
    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(0.00);

    @Enumerated(EnumType.STRING)    
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.USER;

    public enum Role{
        USER,ADMIN,SELLER;
    }

    @ToString.Exclude
    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<Books> bookListings = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "userid",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<RequestSellerRole> requestSellerRole = new ArrayList<>();
    

    @OneToMany(mappedBy = "user",cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    @JsonIgnore
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @ToString.Exclude
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Cart cart;

    @ToString.Exclude
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<Wishlist> wishlists = new ArrayList<>();

    @OneToMany(mappedBy = "buyerEmail",cascade = {CascadeType.PERSIST, CascadeType.REFRESH},orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();

    @ManyToMany(mappedBy = "SellerEmail",cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnore
    @ToString.Exclude
    @Builder.Default
    private List<Payment> payments2 = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    @ToString.Exclude
    private PurchasedBooks purchaseBook;

    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<SalesOverview> salesOverView;

}
