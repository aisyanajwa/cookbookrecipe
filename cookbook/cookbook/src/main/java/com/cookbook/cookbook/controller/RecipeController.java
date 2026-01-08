package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.model.Recipe;
import com.cookbook.cookbook.model.RecipeStatus;
import com.cookbook.cookbook.repository.RecipeRepository;
import com.cookbook.cookbook.service.RecipeDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Recipe operations
 */
@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*")
public class RecipeController {

    @Autowired
    private RecipeDatabase recipeDatabase;

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeDatabase.getApprovedRecipes();
    }

    @GetMapping("/admin")
    public List<Recipe> getAllRecipesForAdmin() {
        return recipeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable String id) {
        try {
            Long recipeId = Long.parseLong(id);
            return recipeRepository.findById(recipeId).orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @PostMapping
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        recipe.setStatus(RecipeStatus.PENDING);
        recipe.setRecipeId(null);
        recipeDatabase.addRecipe(recipe);
        return recipe;
    }

    @PutMapping("/{id}/like")
    public void likeRecipe(@PathVariable String id) {
        try {
            Long recipeId = Long.parseLong(id);
            recipeRepository.findById(recipeId).ifPresent(recipe -> {
                recipe.incrementLike();
                recipeRepository.save(recipe);
            });
        } catch (NumberFormatException e) {
            // Ignore invalid IDs
        }
    }

    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable String id, @RequestBody Recipe updatedRecipe) {
        try {
            Long recipeId = Long.parseLong(id);
            return recipeRepository.findById(recipeId).map(recipe -> {
                recipe.setName(updatedRecipe.getName());
                recipe.setDescription(updatedRecipe.getDescription());
                recipe.setCookTime(updatedRecipe.getCookTime());
                recipe.setServings(updatedRecipe.getServings());
                recipe.setIngredients(updatedRecipe.getIngredients());
                recipe.setInstructions(updatedRecipe.getInstructions());

                if (updatedRecipe.getImageUrl() != null) {
                    recipe.setImageUrl(updatedRecipe.getImageUrl());
                }

                recipe.setStatus(RecipeStatus.PENDING);
                return recipeRepository.save(recipe);
            }).orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @PutMapping("/{id}/status")
    public Recipe updateRecipeStatus(@PathVariable String id, @RequestBody Map<String, String> body) {
        try {
            Long recipeId = Long.parseLong(id);
            String status = body.get("status");
            return recipeRepository.findById(recipeId).map(recipe -> {
                if ("APPROVED".equalsIgnoreCase(status)) {
                    recipe.setStatus(RecipeStatus.APPROVED);
                } else if ("REJECTED".equalsIgnoreCase(status)) {
                    recipe.setStatus(RecipeStatus.REJECTED);
                } else if ("PENDING".equalsIgnoreCase(status)) {
                    recipe.setStatus(RecipeStatus.PENDING);
                }
                return recipeRepository.save(recipe);
            }).orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable String id) {
        try {
            Long recipeId = Long.parseLong(id);
            recipeDatabase.deleteRecipe(recipeId);
        } catch (NumberFormatException e) {
            // Ignore invalid IDs
        }
    }

    @GetMapping("/user/{userId}")
    public List<Recipe> getUserRecipes(@PathVariable String userId) {
        try {
            Long uId = Long.parseLong(userId);
            return recipeDatabase.getRecipesByAuthor(uId);
        } catch (NumberFormatException e) {
            return List.of();
        }
    }
}
