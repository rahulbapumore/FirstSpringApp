package com.example.firstspringapp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/app/v3/")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Credential cred)
    {
        User user = new User();
        user.setUsername(cred.getUsername());
        user.setPassword(cred.getPassword());
        userRepository.save(user);
        return ResponseEntity.ok("User signedup: " + user.getUsername() );
    }

    @PostMapping("update/details")
    public ResponseEntity<?> updateUserDetails(@RequestBody UserView userView, @RequestHeader("Authorization") String token)
    {
        String[] tokenArray = token.split(" ");
        Optional<Token> tokenobj =  tokenRepository.findById(tokenArray[1]);
        if(tokenobj.isPresent())
        {
            if(tokenobj.get().getToken().equals(tokenArray[1]) && tokenobj.get().getUsername().equals(userView.getUsername()))
            {
                User user = new User();
                user.setUsername(userView.getUsername());
                user.setFullName(userView.getFullName());
                user.setEmail(userView.getEmail());
                user.setPhone(userView.getPhone());
                user.setCountry(userView.getCountry());
                userRepository.save(user);
                return ResponseEntity.ok("User updated");
            }
            else {
                return ResponseEntity.badRequest().body("invalid token");
            }
        }
        else
        {
            return ResponseEntity.badRequest().body("empty Token");
        }
    }

    @GetMapping("/get/user/{username}")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token,@PathVariable("username") String username)
    {
        String[] tokenArray = token.split(" ");
        Optional<Token> tokenobj =  tokenRepository.findById(tokenArray[1]);
        if(tokenobj.isPresent()) {
            if (tokenobj.get().getToken().equals(tokenArray[1]) && tokenobj.get().getUsername().equals(username)) {
                User user = userRepository.findById(username).get();
                return ResponseEntity.ok(user.toString());
            }
            else {
                return ResponseEntity.badRequest().body("invalid token");
            }
        }
        else {
            return ResponseEntity.badRequest().body("empty Token");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credential cred)
    {
        Optional<User> user = userRepository.findById(cred.getUsername());
        System.out.println("Rahul "+user);
        if ( user.isPresent() )
        {
            User user1 =  user.get();
            if( user1.getPassword().equals(cred.getPassword()))
            {
                String tk = Utils.generateToken();
                Token token = new Token();
                token.setUsername(user1.getUsername());
                token.setToken(tk);
                tokenRepository.save(token);
                return ResponseEntity.ok().header("Authorization", tk).body("Login successful");
            }
            else
            {
                return ResponseEntity.badRequest().body("Invalid credentials");
            }
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
