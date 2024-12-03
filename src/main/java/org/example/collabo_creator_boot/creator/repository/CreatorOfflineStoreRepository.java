package org.example.collabo_creator_boot.creator.repository;

import org.example.collabo_creator_boot.creator.domain.CreatorOfflineStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorOfflineStoreRepository extends JpaRepository<CreatorOfflineStoreEntity, Long> {
}