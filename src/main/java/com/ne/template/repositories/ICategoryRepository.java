package com.ne.template.repositories;

import com.ne.template.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ICategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByName(String name);
}
