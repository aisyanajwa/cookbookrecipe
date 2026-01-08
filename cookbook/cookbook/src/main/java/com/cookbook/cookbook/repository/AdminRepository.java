package com.cookbook.cookbook.repository;

import com.cookbook.cookbook.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Admin entity
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
