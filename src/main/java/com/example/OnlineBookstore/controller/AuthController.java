package com.example.OnlineBookstore.controller;
import com.example.OnlineBookstore.DTO.LoginRequest;
import com.example.OnlineBookstore.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    // Simulated user authentication service (can be replaced with actual DB check)
    private boolean authenticateUser(String username, String password) {
        // Example: Replace this with a user lookup in your database
        return "admin".equals(username) && "password".equals(password);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        if (authenticateUser(loginRequest.getUsername(), loginRequest.getPassword())) {
            String token = jwtUtil.generateToken(loginRequest.getUsername());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }
}