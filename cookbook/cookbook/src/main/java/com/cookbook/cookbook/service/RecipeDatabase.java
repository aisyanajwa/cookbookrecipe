package com.cookbook.cookbook.service;

import com.cookbook.cookbook.model.Recipe;
import com.cookbook.cookbook.model.RecipeStatus;
import com.cookbook.cookbook.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for Recipe database operations
 * Implements Searchable interface for search functionality
 */
@Service
public class RecipeDatabase implements Searchable {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public ArrayList<Recipe> searchByName(String name) {
        return new ArrayList<>(recipeRepository.findByNameContainingIgnoreCase(name));
    }

    @Override
    public ArrayList<Recipe> searchByIncludedIngredients(String[] ingredients) {
        if (ingredients.length == 0) return new ArrayList<>();
        
        // Mulai dengan resep yang mengandung bahan pertama
        List<Recipe> result = recipeRepository.findByIngredientContaining(ingredients[0]);
        
        // Filter: resep harus mengandung SEMUA bahan yang diminta (AND logic)
        for (int i = 1; i < ingredients.length; i++) {
            List<Recipe> recipesWithIngredient = recipeRepository.findByIngredientContaining(ingredients[i]);
            // Intersect: ambil hanya resep yang ada di kedua list
            result.retainAll(recipesWithIngredient);
        }
        
        return new ArrayList<>(result);
    }

    @Override
    public ArrayList<Recipe> searchByExcludedIngredients(String[] ingredients) {
        if (ingredients.length == 0) return getApprovedRecipes();
        
        // Ambil semua resep yang disetujui
        List<Recipe> allRecipes = recipeRepository.findByStatus(RecipeStatus.APPROVED);
        
        // Filter: buang resep yang mengandung bahan yang dihindari
        List<Recipe> result = allRecipes.stream()
            .filter(recipe -> {
                // Cek apakah resep ini mengandung bahan yang dihindari
                for (String excludedIngredient : ingredients) {
                    for (String recipeIngredient : recipe.getIngredients()) {
                        if (recipeIngredient.toLowerCase().contains(excludedIngredient.toLowerCase())) {
                            return false; // Buang resep ini
                        }
                    }
                }
                return true; // Simpan resep ini
            })
            .toList();
        
        return new ArrayList<>(result);
    }

    public void addRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public ArrayList<Recipe> getApprovedRecipes() {
        return new ArrayList<>(recipeRepository.findByStatus(RecipeStatus.APPROVED));
    }

    public ArrayList<Recipe> getPendingRecipes() {
        return new ArrayList<>(recipeRepository.findByStatus(RecipeStatus.PENDING));
    }

    public void updateRecipeStatus(String recipeId, RecipeStatus status) {
        try {
            Long id = Long.parseLong(recipeId);
            recipeRepository.findById(id).ifPresent(recipe -> {
                recipe.setStatus(status);
                recipeRepository.save(recipe);
            });
        } catch (NumberFormatException e) {
            // Handle error or ignore
        }
    }

    public void deleteRecipe(Long id) {
        recipeRepository.findById(id).ifPresent(recipe -> {
            // Delete image file if it exists and is local
            String imageUrl = recipe.getImageUrl();
            if (imageUrl != null && imageUrl.startsWith("/uploads/")) {
                try {
                    String fileName = imageUrl.substring(9);
                    Path path = Paths.get(System.getProperty("user.dir"), "uploads", fileName);
                    boolean deleted = Files.deleteIfExists(path);
                    if (deleted) {
                        System.out.println("Deleted image file: " + path.toString());
                    }
                } catch (Exception e) {
                    System.err.println("Failed to delete image file: " + e.getMessage());
                }
            }
            recipeRepository.delete(recipe);
        });
    }

    public ArrayList<Recipe> getRecipesByAuthor(Long authorId) {
        return new ArrayList<>(recipeRepository.findByUploadedBy(authorId));
    }
}
