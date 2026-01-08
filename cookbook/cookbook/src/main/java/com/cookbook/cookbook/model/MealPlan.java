package com.cookbook.cookbook.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * MealPlan entity - stores user's meal planning
 * Links a recipe to a specific day for a user
 */
@Entity
@Table(name = "meal_plans")
public class MealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mealplan_id")
    private Long mealplanId;

    @Column(nullable = false)
    private String day;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    // Constructors
    public MealPlan() {}

    public MealPlan(String day, String menuName, User user) {
        this.day = day;
        this.menuName = menuName;
        this.user = user;
    }

    // Getters and Setters
    public Long getMealplanId() { 
        return mealplanId; 
    }

    public void setMealplanId(Long mealplanId) { 
        this.mealplanId = mealplanId; 
    }

    public String getDay() { 
        return day; 
    }

    public void setDay(String day) { 
        this.day = day; 
    }

    public String getMenuName() { 
        return menuName; 
    }

    public void setMenuName(String menuName) { 
        this.menuName = menuName; 
    }

    public User getUser() { 
        return user; 
    }

    public void setUser(User user) { 
        this.user = user; 
    }

    public Recipe getRecipe() { 
        return recipe; 
    }

    public void setRecipe(Recipe recipe) { 
        this.recipe = recipe; 
    }
}
