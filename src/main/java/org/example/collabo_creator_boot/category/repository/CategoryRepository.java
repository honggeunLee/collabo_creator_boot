package org.example.collabo_creator_boot.category.repository;

import org.example.collabo_creator_boot.category.domain.CategoryEntity;
import org.example.collabo_creator_boot.category.repository.search.CategorySearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>, CategorySearch {

    @Query("SELECT c FROM CategoryEntity c WHERE c.creatorEntity.creatorId = :creatorId")
    List<CategoryEntity> findCategoriesByCreatorId(@Param("creatorId") String creatorId);

}
