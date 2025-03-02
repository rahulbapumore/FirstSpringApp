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

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Credential cred)
    {
        User user = new User();
        user.setUsername(cred.getUsername());
        user.setPassword(cred.getPassword());
        userRepository.save(user);
        return ResponseEntity.ok("User signedup: " + user.getUsername() );
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
                return ResponseEntity.ok().header("Authorization", Utils.generateToken()).body("Login successful");
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
