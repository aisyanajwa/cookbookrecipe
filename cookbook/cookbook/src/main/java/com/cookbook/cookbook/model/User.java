package com.cookbook.cookbook.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

/**
 * User entity - extends Account with user-specific features
 * Manages liked recipes, saved categories, and meal plans
 */
@Entity
@Table(name = "users")
@PrimaryKeyJoinColumn(name = "account_id")
public class User extends Account {

    @ManyToMany
    @JoinTable(
        name = "likes",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "account_id"),
        inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    @JsonIgnore
    private List<Recipe> likedRecipes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SavedCategory> savedCategories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MealPlan> mealPlans = new ArrayList<>();

    // Constructors
    public User() {}

    public User(String username, String password, String email) {
        super(username, password, Role.USER, email);
        this.likedRecipes = new ArrayList<>();
        this.savedCategories = new ArrayList<>();
        this.mealPlans = new ArrayList<>();
    }

    // Getters
    public List<Recipe> getLikedRecipes() {
        return likedRecipes;
    }

    public List<SavedCategory> getSavedCategories() {
        return savedCategories;
    }

    public List<MealPlan> getMealPlans() { 
        return mealPlans; 
    }

    // Business Methods
    public void likeRecipe(Recipe recipe) {
        likedRecipes.add(recipe);
        recipe.incrementLike();
    }

    public void addSavedCategory(SavedCategory category) {
        category.setUser(this);
        savedCategories.add(category);
    }

    public void addMealPlan(MealPlan mealPlan) {
        mealPlans.add(mealPlan);
        mealPlan.setUser(this);
    }
}
