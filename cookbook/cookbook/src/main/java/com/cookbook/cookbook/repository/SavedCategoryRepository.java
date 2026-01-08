package com.cookbook.cookbook.repository;

import com.cookbook.cookbook.model.SavedCategory;
import com.cookbook.cookbook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for SavedCategory entity
 */
@Repository
public interface SavedCategoryRepository extends JpaRepository<SavedCategory, Long> {
    List<SavedCategory> findByUser(User user);
    SavedCategory findByUserAndCategoryName(User user, String categoryName);
}
