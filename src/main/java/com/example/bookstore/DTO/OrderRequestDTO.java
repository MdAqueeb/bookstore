package com.example.bookstore.DTO;


import java.math.BigDecimal;
import java.util.List;

import lombok.Data;


@Data
public class OrderRequestDTO {

    private Long userId;
    private List<OrderItemDTO> orderItems;
    private BigDecimal totalAmount;
    private String shippingAddress;

    // Getters and Setters

    

    @Data
    public static class OrderItemDTO {
        private Long bookId;
        // private int quantity;
        private BigDecimal price;
        private Long sellerId;

        // Getters and Setters
       
    }
}

