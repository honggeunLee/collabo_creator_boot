package org.example.collabo_creator_boot.creator.repository;

import org.example.collabo_creator_boot.creator.domain.CreatorEntity;
import org.example.collabo_creator_boot.creator.repository.search.CreatorSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorRepository extends JpaRepository<CreatorEntity,String>, CreatorSearch {
}
