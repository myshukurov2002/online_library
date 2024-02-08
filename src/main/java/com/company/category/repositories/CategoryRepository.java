package com.company.category.repositories;

import com.company.category.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    Optional<CategoryEntity> findByNameAndVisibilityTrue(String name);

    Optional<CategoryEntity> findByIdAndVisibilityTrue(UUID id);

    List<CategoryEntity> findAllByVisibilityTrue();
}
