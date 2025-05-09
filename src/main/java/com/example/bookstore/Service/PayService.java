package com.example.bookstore.Service;

import org.springframework.stereotype.Service;

import com.example.bookstore.DTO.RazorpaymentDTO;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

// import lombok.Data;
// import lombok.Value;

@Service
// @Data
public class PayService {

    @Value("${razorpay.api.key}")
    private String apikey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;

    public Order createOrder(com.example.bookstore.Entities.Order orders) throws RazorpayException{
        RazorpayClient razorpayClient =  new RazorpayClient(apikey, apiSecret);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", orders.getTotalAmount().multiply(BigDecimal.valueOf(100)).intValue());
        jsonObject.put("currency", "INR");
        jsonObject.put("receipt", orders.getUser().getEmail());

        Order order = razorpayClient.orders.create(jsonObject);
        return order;
    }

    public boolean verifySignature(RazorpaymentDTO dto) {
        try{
            String orderId = dto.getRazorpayOrderId();
            String paymentId = dto.getRazorpayPaymentId();
            String signature = dto.getRazorpaySignature();

            String data =  orderId+"|"+paymentId;
            String generatedSignature = Utils.getHash(data, apiSecret);
            return generatedSignature.equals(signature);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
