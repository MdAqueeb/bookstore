package com.example.bookstore.Entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.ToString;
import jakarta.persistence.SequenceGenerator;

@Entity
@Data
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cart_seq")
    @SequenceGenerator(name = "cart_seq", sequenceName = "cart_sequence", allocationSize = 1)
    private long cartid;

    // @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userid")
    @ToString.Exclude
    private User user;

    // @ToString.Exclude
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>(); // Ensure this is a collection

    @ManyToMany(cascade = CascadeType.PERSIST)  // Books relationship
    @JsonIgnore
    @ToString.Exclude
    @JoinTable(
        name = "cart_books", 
        joinColumns = @JoinColumn(name = "cartid"), 
        inverseJoinColumns = @JoinColumn(name = "bookid"))
    private List<Books> books = new ArrayList<>();

    @Column(name = "totalamount")
    private BigDecimal totalAmount;

    @Column(name = "totalitems")
    private int totalItems;

    @PrePersist
    @PreUpdate
    public void calculateCartDetails() {
        BigDecimal total = BigDecimal.ZERO;
        int itemsCount = 0;
    
        for (Books book : books) {
            total = total.add(book.getPrice());
            itemsCount++;
        }
    
        totalAmount = total;
        totalItems = itemsCount;
    }
}
