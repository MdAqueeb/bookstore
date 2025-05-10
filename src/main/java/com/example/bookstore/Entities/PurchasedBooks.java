package com.example.bookstore.Entities;

// import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// import com.fasterxml.jackson.annotation.JsonManagedReference;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
// import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
// import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "books")
public class PurchasedBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "userid", unique = true) 
    private User user;

    // @ToString.Exclude
    @Builder.Default
    @ManyToMany
    @JoinTable(
        name = "purchased_books_items",
        joinColumns = @JoinColumn(name = "purchased_books_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Books> books = new ArrayList<>();

    // @Column(nullable =  false)
    // private LocalDateTime orderdate;
}
