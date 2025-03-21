package com.example.bookstore.Entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor  // Required for Hibernate
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

    @Builder.Default
    private String avator = "https://wallpapers.com/images/hd/user-profile-placeholder-icon-1val0kp6a7ji4vsi.png";

    @Enumerated(EnumType.STRING)    
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.USER;

    public enum Role{
        USER,ADMIN;
    }

    // Add favorities method one to many relationship;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Wishlist> wishlist = new ArrayList<>();


    // i have to show all orders what i have order from past so i not use all if any other type is missing then provide
    @OneToMany(mappedBy = "user",cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Cart> cart = new ArrayList<>();
}
