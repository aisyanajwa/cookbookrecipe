package com.cookbook.cookbook.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

/**
 * Recipe entity - stores recipe information
 * Includes ingredients, instructions, and metadata
 */
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(
        name = "recipe_ingredients",
        joinColumns = @JoinColumn(name = "recipe_id")
    )
    @Column(name = "ingredient_name")
    private List<String> ingredients = new ArrayList<>();

    @Column(length = 1000)
    private String description;

    @ElementCollection
    @CollectionTable(
        name = "recipe_instructions",
        joinColumns = @JoinColumn(name = "recipe_id")
    )
    @Column(name = "instruction", length = 500)
    private List<String> instructions = new ArrayList<>();

    @Column(name = "cook_time")
    private String cookTime;

    private Integer servings;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecipeStatus status = RecipeStatus.PENDING;

    @Column(name = "like_count", nullable = false)
    private int likeCount = 0;

    @Column(name = "uploaded_by")
    private Long uploadedBy;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "uploaded_by", referencedColumnName = "account_id", insertable = false, updatable = false)
    @JsonIgnoreProperties({"likedRecipes", "savedCategories", "mealPlans", "password"})
    private Account author;

    // Constructors
    public Recipe() {}

    public Recipe(String name, List<String> ingredients, List<String> instructions, Long uploadedBy) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.uploadedBy = uploadedBy;
        this.status = RecipeStatus.PENDING;
        this.likeCount = 0;
    }

    // Getters and Setters
    public Long getRecipeId() { 
        return recipeId; 
    }

    public void setRecipeId(Long recipeId) { 
        this.recipeId = recipeId; 
    }

    public String getName() { 
        return name; 
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public List<String> getIngredients() { 
        return ingredients; 
    }

    public void setIngredients(List<String> ingredients) { 
        this.ingredients = ingredients; 
    }

    public List<String> getInstructions() { 
        return instructions; 
    }

    public void setInstructions(List<String> instructions) { 
        this.instructions = instructions; 
    }

    public String getDescription() { 
        return description; 
    }

    public void setDescription(String description) { 
        this.description = description; 
    }

    public String getCookTime() { 
        return cookTime; 
    }

    public void setCookTime(String cookTime) { 
        this.cookTime = cookTime; 
    }

    public Integer getServings() { 
        return servings; 
    }

    public void setServings(Integer servings) { 
        this.servings = servings; 
    }

    public RecipeStatus getStatus() { 
        return status; 
    }

    public void setStatus(RecipeStatus status) { 
        this.status = status; 
    }

    public int getLikeCount() { 
        return likeCount; 
    }

    public void setLikeCount(int likeCount) { 
        this.likeCount = likeCount; 
    }

    public Long getUploadedBy() { 
        return uploadedBy; 
    }

    public void setUploadedBy(Long uploadedBy) { 
        this.uploadedBy = uploadedBy; 
    }

    public String getImageUrl() { 
        return imageUrl; 
    }

    public void setImageUrl(String imageUrl) { 
        this.imageUrl = imageUrl; 
    }

    public Account getAuthor() { 
        return author; 
    }

    // Business Methods
    public void incrementLike() {
        likeCount++;
    }
}
