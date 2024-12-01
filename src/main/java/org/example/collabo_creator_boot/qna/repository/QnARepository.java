package org.example.collabo_creator_boot.qna.repository;

import org.example.collabo_creator_boot.qna.domain.QnAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnARepository extends JpaRepository<QnAEntity, Long> {
}
