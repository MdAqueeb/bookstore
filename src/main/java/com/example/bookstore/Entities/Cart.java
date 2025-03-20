package com.example.bookstore.Entities;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartid;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @ManyToMany(mappedBy = "cart")
    private List<Books> books;

    // check the below any missing
    @OneToOne(mappedBy = "cart")
    private Order orders;

    @Transient
    private BigDecimal totalAmount;

    @Transient
    private int totalItems;

    @PrePersist
    @PreUpdate
    public void calculateCartDetails() {
        BigDecimal total = BigDecimal.ZERO;
        int itemsCount = 0;
    
        for (Books book : books) {
            total = total.add(book.getPrice());  // Add the price of each book
            itemsCount++;  // Increment the item count
        }
    
        totalAmount = total;
        totalItems = itemsCount;
    }    
    
}