package com.example.firstspringapp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
public class UserView {
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String country;
}
