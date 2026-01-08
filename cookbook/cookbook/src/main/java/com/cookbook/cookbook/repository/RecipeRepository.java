package com.cookbook.cookbook.repository;

import com.cookbook.cookbook.model.Recipe;
import com.cookbook.cookbook.model.RecipeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
