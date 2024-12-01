package org.example.collabo_creator_boot.review.service;

import lombok.RequiredArgsConstructor;
import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.review.domain.ReviewEntity;
import org.example.collabo_creator_boot.review.dto.ReviewListDTO;
import org.example.collabo_creator_boot.review.repository.ReviewRepository;
import org.example.collabo_creator_boot.review.repository.search.ReviewSearch;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewSearch reviewSearch;

    public PageResponseDTO<ReviewListDTO> getReviewList(String creatorId, PageRequestDTO pageRequestDTO) {
        return reviewSearch.reviewListByCreator(creatorId, pageRequestDTO);
    }

    public void addReply(Long reviewNo, String reply) {
        // 기존 리뷰 엔티티 조회
        ReviewEntity existingReview = reviewRepository.findById(reviewNo)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // Builder를 사용해 답변 추가
        ReviewEntity updatedReview = ReviewEntity.builder()
                .reviewNo(existingReview.getReviewNo())
                .rating(existingReview.getRating())
                .comment(existingReview.getComment())
                .reply(reply) // 답변 추가
                .productEntity(existingReview.getProductEntity())
                .customerEntity(existingReview.getCustomerEntity())
                .build();

        // 엔티티 저장
        reviewRepository.save(updatedReview);
    }

}
