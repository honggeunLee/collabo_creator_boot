package org.example.collabo_creator_boot.review.repository;

import org.example.collabo_creator_boot.review.domain.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}
