package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.model.*;
import com.cookbook.cookbook.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for User operations
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private SavedCategoryRepository savedCategoryRepository;

    @PostMapping("/{userId}/like/{recipeId}")
    public Map<String, Object> likeRecipe(@PathVariable Long userId, @PathVariable Long recipeId) {
        User user = userRepository.findById(userId).orElse(null);
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);

        if (user != null && recipe != null) {
            if (!user.getLikedRecipes().contains(recipe)) {
                user.likeRecipe(recipe);
                userRepository.save(user);
                recipeRepository.save(recipe);
                return Map.of("success", true, "likes", recipe.getLikeCount());
            } else {
                return Map.of("success", false, "message", "Already liked");
            }
        }

        String error = "";
        if (user == null) error += "User " + userId + " not found. ";
        if (recipe == null) error += "Recipe " + recipeId + " not found.";
        return Map.of("success", false, "message", error);
    }

    @PostMapping("/{userId}/unlike/{recipeId}")
    public Map<String, Object> unlikeRecipe(@PathVariable Long userId, @PathVariable Long recipeId) {
        User user = userRepository.findById(userId).orElse(null);
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);

        if (user != null && recipe != null) {
            if (user.getLikedRecipes().contains(recipe)) {
                user.getLikedRecipes().remove(recipe);
                int newLikes = Math.max(0, recipe.getLikeCount() - 1);
                recipe.setLikeCount(newLikes);

                userRepository.save(user);
                recipeRepository.save(recipe);
                return Map.of("success", true, "likes", newLikes);
            } else {
                return Map.of("success", false, "message", "Not liked yet");
            }
        }
        return Map.of("success", false, "message", "User or Recipe not found");
    }

    @PostMapping("/{userId}/save/{recipeId}")
    public Map<String, Object> saveRecipe(
            @PathVariable Long userId,
            @PathVariable Long recipeId,
            @RequestBody Map<String, String> body) {
        
        User user = userRepository.findById(userId).orElse(null);
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        String categoryName = body.get("category");

        if (user != null && recipe != null && categoryName != null) {
            SavedCategory category = savedCategoryRepository.findByUserAndCategoryName(user, categoryName);

            if (category == null) {
                category = new SavedCategory(categoryName);
                user.addSavedCategory(category);
            }

            if (!category.getRecipes().contains(recipe)) {
                category.addRecipe(recipe);
                userRepository.save(user);
                return Map.of("success", true);
            }
            return Map.of("success", false, "message", "Already saved in this category");
        }
        return Map.of("success", false, "message", "Invalid Request");
    }

    @PostMapping("/{userId}/unsave/{recipeId}")
    public Map<String, Object> unsaveRecipe(
            @PathVariable Long userId,
            @PathVariable Long recipeId,
            @RequestBody Map<String, String> body) {
        
        User user = userRepository.findById(userId).orElse(null);
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        String categoryName = body.get("category");

        if (user != null && recipe != null) {
            if (categoryName != null) {
                SavedCategory category = savedCategoryRepository.findByUserAndCategoryName(user, categoryName);
                if (category != null) {
                    category.getRecipes().remove(recipe);
                    savedCategoryRepository.save(category);
                }
            } else {
                for (SavedCategory sc : user.getSavedCategories()) {
                    if (sc.getRecipes().contains(recipe)) {
                        sc.getRecipes().remove(recipe);
                        savedCategoryRepository.save(sc);
                    }
                }
            }
            return Map.of("success", true);
        }
        return Map.of("success", false, "message", "User or Recipe not found");
    }

    @GetMapping("/{userId}/saved")
    public List<SavedCategory> getSavedCategories(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getSavedCategories();
        }
        return List.of();
    }

    @GetMapping("/{userId}/liked")
    public List<Recipe> getLikedRecipes(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getLikedRecipes();
        }
        return List.of();
    }
}
