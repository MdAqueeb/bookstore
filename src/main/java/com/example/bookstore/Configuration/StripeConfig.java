// package com.example.bookstore.Configuration;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
// import org.springframework.context.annotation.Configuration;

// import com.stripe.Stripe;
// import jakarta.annotation.PostConstruct;

// @Configuration
// @ConditionalOnProperty(name = "stripe.api.key")
// public class StripeConfig {

//     @Value("${stripe.api.key}")
//     private String stripeApiKey;

//     @PostConstruct
//     public void init() {
//         // Initialize Stripe with the API key from application properties
//         // Commented out for now to avoid errors with missing Stripe dependency
//         // Stripe.apiKey = stripeApiKey;
//         System.out.println("Initialized Stripe with API key: " + stripeApiKey.substring(0, 4) + "...");
//     }
// } 