package com.example.bookstore.Entities;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.ToString;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Column;
@Entity
@Data
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "userid")
    @NotNull
    private User user;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "bookid")
    @NotNull
    private Books book;
}