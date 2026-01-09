package com.cookbook.cookbook.repository;

import com.cookbook.cookbook.model.Recipe;
import com.cookbook.cookbook.model.RecipeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for Recipe entity
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByNameContainingIgnoreCase(String name);
    List<Recipe> findByStatus(RecipeStatus status);
    List<Recipe> findByUploadedBy(Long uploadedBy);
    
    /**
     * Mencari resep yang mengandung bahan tertentu (case-insensitive)
     * Query ini join ke tabel recipe_ingredients (ElementCollection)
     */
    @Query("SELECT DISTINCT r FROM Recipe r JOIN r.ingredients i WHERE LOWER(i) LIKE LOWER(CONCAT('%', :ingredient, '%'))")
    List<Recipe> findByIngredientContaining(@Param("ingredient") String ingredient);
}
