package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.model.Recipe;
import com.cookbook.cookbook.model.RecipeStatus;
import com.cookbook.cookbook.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Admin operations
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/pending")
    public List<Recipe> getPendingRecipes() {
        return recipeRepository.findByStatus(RecipeStatus.PENDING);
    }

    @PostMapping("/approve/{id}")
    public void approveRecipe(@PathVariable String id) {
        try {
            Long recipeId = Long.parseLong(id);
            recipeRepository.findById(recipeId).ifPresent(recipe -> {
                recipe.setStatus(RecipeStatus.APPROVED);
                recipeRepository.save(recipe);
            });
        } catch (NumberFormatException e) {
            // Ignore invalid IDs
        }
    }

    @PostMapping("/reject/{id}")
    public void rejectRecipe(@PathVariable String id) {
        try {
            Long recipeId = Long.parseLong(id);
            recipeRepository.findById(recipeId).ifPresent(recipe -> {
                recipe.setStatus(RecipeStatus.REJECTED);
                recipeRepository.save(recipe);
            });
        } catch (NumberFormatException e) {
            // Ignore invalid IDs
        }
    }
}
