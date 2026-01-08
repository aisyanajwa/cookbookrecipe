package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.model.Account;
import com.cookbook.cookbook.model.Admin;
import com.cookbook.cookbook.model.User;
import com.cookbook.cookbook.repository.AccountRepository;
import com.cookbook.cookbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller for Authentication operations
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        Map<String, Object> response = new HashMap<>();

        Account account = accountRepository.findByUsername(username);
        if (account != null) {
            if (account.login(username, password)) {
                response.put("success", true);
                response.put("userId", account.getId());
                if (account instanceof Admin) {
                    response.put("role", "admin");
                } else {
                    response.put("role", "user");
                }
                response.put("username", username);
                return response;
            }
        }

        response.put("success", false);
        response.put("message", "Invalid credentials");
        return response;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String email = body.getOrDefault("email", username + "@email.com");
        String roleStr = body.getOrDefault("role", "USER");

        Map<String, Object> response = new HashMap<>();

        if (accountRepository.findByUsername(username) != null) {
            response.put("success", false);
            response.put("message", "Username already exists");
            return response;
        }

        Account newAccount;
        if (roleStr.equalsIgnoreCase("ADMIN")) {
            newAccount = new Admin(username, password, email);
            accountRepository.save(newAccount);
        } else {
            newAccount = new User(username, password, email);
            userRepository.save((User) newAccount);
        }

        response.put("success", true);
        response.put("username", username);
        response.put("role", roleStr.toUpperCase());
        response.put("userId", newAccount.getId());
        return response;
    }
}