package com.example.firstspringapp;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TokenRepository extends MongoRepository<Token, String> {

    List<Token> getTokenByToken(String token);
}
