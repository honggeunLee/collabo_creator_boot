package org.example.collabo_creator_boot.qna.service;

import lombok.RequiredArgsConstructor;
import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.qna.domain.QnAEntity;
import org.example.collabo_creator_boot.qna.dto.QnAListDTO;
import org.example.collabo_creator_boot.qna.repository.QnARepository;
import org.example.collabo_creator_boot.qna.repository.search.QnASearch;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QnAService {
    private final QnARepository qnARepository;
    private final QnASearch qnASearch;

    public PageResponseDTO<QnAListDTO> getQnAListByCreator(String creatorId, PageRequestDTO pageRequestDTO) {
        return qnASearch.qnAListByCreator(creatorId, pageRequestDTO);
    }

    public void addAnswer(Long qnaNo, String answer) {
        // 기존 리뷰 엔티티 조회
        QnAEntity existingQnA = qnARepository.findById(qnaNo)
                .orElseThrow(() -> new RuntimeException("QnA not found"));

        // Builder를 사용해 답변 추가
        QnAEntity updatedQnA = QnAEntity.builder()
                .qnaNo(existingQnA.getQnaNo())
                .productEntity(existingQnA.getProductEntity())
                .customerEntity(existingQnA.getCustomerEntity())
                .question(existingQnA.getQuestion())
                .answer(answer) // 답변 추가
                .creatorEntity(existingQnA.getCreatorEntity())
                .build();

        // 엔티티 저장
        qnARepository.save(updatedQnA);
    }
}
