package com.example.bookstore.Entities;

import java.math.BigDecimal;
import java.util.HashMap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesOverview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long salesId;

    @ManyToOne
    @JoinColumn(name = "sellerid")
    private User seller;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal  TotalAmount = BigDecimal.valueOf(0);

    @Column(nullable = false)
    @Builder.Default
    private int Ordercount = 0;


    @Column(name = "nameCount")
    @Builder.Default
    private HashMap<String,Integer> var= new HashMap<>();
}
