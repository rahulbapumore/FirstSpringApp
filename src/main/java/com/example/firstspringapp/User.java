package com.example.firstspringapp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String username;
    private String fullName;
    private String password;
    private String email;
    private String phone;
    private String country;
}
