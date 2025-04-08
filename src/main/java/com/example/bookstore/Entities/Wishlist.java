    package com.example.bookstore.Entities;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonProperty;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import lombok.Data;
    import lombok.ToString;
    import jakarta.persistence.Entity;
    import jakarta.persistence.FetchType;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.ManyToOne;
    import jakarta.persistence.Table;
    import jakarta.persistence.Column;
    import lombok.Data;

    @Entity
    @Data
    @Table(name = "wishlist")
    public class Wishlist {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @JoinColumn(name = "userid")

        // @JsonIgnore
        private long user;

        @JoinColumn(name = "bookid")
        // @JsonIgnore
        private long book;
    }