package com.example.bookstore.DTO;

import com.example.bookstore.Entities.User;

public class Respon {

    private User fields;
    private String Token;

    // Constructor
    public Respon(User fields, String Token){
        this.fields = fields;
        this.Token = Token;
    }

    // Getter and Setter for fields
    public User getFields() {
        return fields;
    }

    public void setFields(User fields) {
        this.fields = fields;
    }

    // Getter and Setter for Token
    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        this.Token = token;
    }
}
