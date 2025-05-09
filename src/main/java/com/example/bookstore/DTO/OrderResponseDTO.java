package com.example.bookstore.DTO;


import java.util.ArrayList;

// import java.math.BigDecimal;
// import java.util.List;

// import com.example.bookstore.Entities.Books;
// import com.example.bookstore.Entities.Books;
// import com.example.bookstore.Entities.Cart;
// import com.example.bookstore.Entities.OrderItem;

import lombok.Data;


@Data
public class OrderResponseDTO {

    private String razorpayOrderId;
    private int amount;
    private ArrayList<String> bookTitle = new ArrayList<>();
    private String key;

}

