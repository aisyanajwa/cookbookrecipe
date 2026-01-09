package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")
public class SearchController {

    @Autowired
    private com.cookbook.cookbook.service.Searchable recipeDatabase;

    /**
     * Pencarian berdasarkan nama resep
     * GET /api/search?query=nasi
     */
    @GetMapping
    public List<Recipe> search(@RequestParam String query) {
        return recipeDatabase.searchByName(query);
    }

    /**
     * Pencarian resep yang mengandung bahan tertentu (Include)
     * GET /api/search/include?ingredients=ayam,bawang
     */
    @GetMapping("/include")
    public List<Recipe> searchByIncludedIngredients(@RequestParam String[] ingredients) {
        return recipeDatabase.searchByIncludedIngredients(ingredients);
    }

    /**
     * Pencarian resep yang TIDAK mengandung bahan tertentu (Exclude)
     * GET /api/search/exclude?ingredients=kacang,susu
     */
    @GetMapping("/exclude")
    public List<Recipe> searchByExcludedIngredients(@RequestParam String[] ingredients) {
        return recipeDatabase.searchByExcludedIngredients(ingredients);
    }
}

