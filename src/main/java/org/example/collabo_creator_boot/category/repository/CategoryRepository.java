package org.example.collabo_creator_boot.category.repository;

import org.example.collabo_creator_boot.category.domain.CategoryEntity;
import org.example.collabo_creator_boot.category.repository.search.CategorySearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>, CategorySearch {
}
