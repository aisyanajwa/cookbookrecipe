package com.cookbook.cookbook.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SavedCategory entity - user's custom category for saving recipes
 * Allows users to organize saved recipes into categories
 */
@Entity
@Table(name = "saved_categories")
public class SavedCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @ManyToMany
    @JoinTable(
        name = "saved_recipes",
        joinColumns = @JoinColumn(name = "category_id"),
        inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private List<Recipe> recipes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors
    public SavedCategory() {}

    public SavedCategory(String categoryName) {
        this.categoryName = categoryName;
        this.recipes = new ArrayList<>();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Business Methods
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }
}
