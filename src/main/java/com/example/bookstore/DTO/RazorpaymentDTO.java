package com.example.bookstore.DTO;

import lombok.Data;

@Data
public class RazorpaymentDTO {
    private String razorpayPaymentId;
    private String razorpayOrderId;
    private String razorpaySignature;
}
