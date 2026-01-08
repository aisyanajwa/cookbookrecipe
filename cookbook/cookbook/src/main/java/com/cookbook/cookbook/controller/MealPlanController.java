package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.model.MealPlan;
import com.cookbook.cookbook.model.User;
import com.cookbook.cookbook.repository.MealPlanRepository;
import com.cookbook.cookbook.repository.RecipeRepository;
import com.cookbook.cookbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for MealPlan operations
 */
@RestController
@RequestMapping("/api/mealplans")
@CrossOrigin(origins = "*")
public class MealPlanController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @PostMapping("/{userId}/add")
    public MealPlan addMeal(@PathVariable String userId, @RequestBody Map<String, String> body) {
        try {
            Long uId = Long.parseLong(userId);
            return userRepository.findById(uId).map(user -> {
                String day = body.get("day");
                String menu = body.get("menu");
                String recipeIdStr = body.get("recipeId");

                if (day != null && menu != null) {
                    MealPlan meal = new MealPlan(day, menu, user);

                    if (recipeIdStr != null) {
                        try {
                            Long rId = Long.parseLong(recipeIdStr);
                            recipeRepository.findById(rId).ifPresent(recipe -> {
                                meal.setRecipe(recipe);
                            });
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Recipe ID format: " + recipeIdStr);
                        }
                    }

                    return mealPlanRepository.save(meal);
                }
                return null;
            }).orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @GetMapping("/{userId}")
    public List<MealPlan> getMealPlan(@PathVariable String userId) {
        try {
            Long uId = Long.parseLong(userId);
            return userRepository.findById(uId)
                .map(user -> mealPlanRepository.findByUser(user))
                .orElse(new ArrayList<>());
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMeal(@PathVariable String id) {
        try {
            Long mId = Long.parseLong(id);
            mealPlanRepository.deleteById(mId);
        } catch (NumberFormatException e) {
            // Ignore invalid IDs
        }
    }
}
