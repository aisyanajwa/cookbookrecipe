package com.cookbook.cookbook.model;

import jakarta.persistence.*;

/**
 * Admin entity - extends Account with admin-specific features
 * Can approve or reject recipes
 */
@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "account_id")
public class Admin extends Account {

    // Constructors
    public Admin() {}

    public Admin(String username, String password, String email) {
        super(username, password, Role.ADMIN, email);
    }

    // Business Methods
    public void approveRecipe(Recipe recipe) {
        recipe.setStatus(RecipeStatus.APPROVED);
    }

    public void rejectRecipe(Recipe recipe) {
        recipe.setStatus(RecipeStatus.REJECTED);
    }
}
