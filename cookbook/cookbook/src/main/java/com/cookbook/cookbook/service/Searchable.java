package com.cookbook.cookbook.service;

import com.cookbook.cookbook.model.Recipe;
import java.util.ArrayList;

public interface Searchable {
    ArrayList<Recipe> searchByName(String name);
    ArrayList<Recipe> searchByIncludedIngredients(String[] ingredients);
    ArrayList<Recipe> searchByExcludedIngredients(String[] ingredients);
}
