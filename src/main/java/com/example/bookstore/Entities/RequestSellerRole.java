package com.example.bookstore.Entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.PrePersist;

@Entity
@Data
@Builder
@Table(name = "RequestRole")
@NoArgsConstructor
@AllArgsConstructor

public class RequestSellerRole {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "reason", nullable = false, columnDefinition = "LONGTEXT")
    private String Reason;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User userid;

    private LocalDate date;

    @Enumerated(EnumType.STRING)    
    @Column(nullable = false)
    @Builder.Default
    private Status status = Status.PENDING;

    @PrePersist
    protected void onCreate() {
        date = LocalDate.now();
    }
    

    public enum Status{
        PENDING,REJECTED;
    }
}
