package org.example.collabo_creator_boot.creatorlogin.repository;

import org.example.collabo_creator_boot.creator.domain.CreatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorLoginRepository extends JpaRepository<CreatorEntity, String> {

}
