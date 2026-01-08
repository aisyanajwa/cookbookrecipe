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

    @GetMapping
    public List<Recipe> search(@RequestParam String query) {
        return recipeDatabase.searchByName(query);
    }
}
