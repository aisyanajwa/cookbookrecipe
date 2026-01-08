package com.cookbook.cookbook.repository;

import com.cookbook.cookbook.model.MealPlan;
import com.cookbook.cookbook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for MealPlan entity
 */
@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    List<MealPlan> findByUser(User user);
}
