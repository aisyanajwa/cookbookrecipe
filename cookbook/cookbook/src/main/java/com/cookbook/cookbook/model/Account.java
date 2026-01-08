package com.cookbook.cookbook.model;

import jakarta.persistence.*;

/**
 * Base class for all account types (User, Admin)
 * Uses JOINED inheritance strategy for separate tables
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "accounts")
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    protected Long id;

    @Column(nullable = false)
    protected String username;

    @Column(nullable = false)
    protected String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected Role role;

    @Column(nullable = false)
    protected String email;

    // Constructors
    public Account() {}

    public Account(String username, String password, Role role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public String getUsername() { 
        return username; 
    }

    public void setUsername(String username) { 
        this.username = username; 
    }

    public String getPassword() { 
        return password; 
    }

    public void setPassword(String password) { 
        this.password = password; 
    }

    public Role getRole() { 
        return role; 
    }

    public void setRole(Role role) { 
        this.role = role; 
    }

    public String getEmail() { 
        return email; 
    }

    public void setEmail(String email) { 
        this.email = email; 
    }

    // Business Methods
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}
