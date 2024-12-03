package org.example.collabo_creator_boot.review.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.creator.domain.QCreatorEntity;
import org.example.collabo_creator_boot.product.domain.QProductEntity;
import org.example.collabo_creator_boot.review.domain.QReviewEntity;
import org.example.collabo_creator_boot.review.domain.QReviewImageEntity;
import org.example.collabo_creator_boot.review.domain.ReviewEntity;
import org.example.collabo_creator_boot.review.dto.ReviewImageDTO;
import org.example.collabo_creator_boot.review.dto.ReviewListDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ReviewSearchImpl extends QuerydslRepositorySupport implements ReviewSearch {

    public ReviewSearchImpl() {
        super(ReviewEntity.class);
    }

    @Override
    public PageResponseDTO<ReviewListDTO> reviewListByCreator(String creatorId, PageRequestDTO pageRequestDTO) {
        // 페이징 설정
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("createdAt").descending());

        QReviewEntity review = QReviewEntity.reviewEntity;
        QProductEntity product = QProductEntity.productEntity;
        QCreatorEntity creator = QCreatorEntity.creatorEntity;
        QReviewImageEntity reviewImage = QReviewImageEntity.reviewImageEntity;

        // 기본 리뷰 데이터 조회
        JPQLQuery<ReviewListDTO> query = from(review)
                .leftJoin(review.productEntity, product)                 // 리뷰 -> 상품
                .leftJoin(product.creatorEntity, creator)                // 상품 -> 창작자
                .where(review.creatorEntity.creatorId.eq(creatorId))
                .select(Projections.bean(ReviewListDTO.class,
                        review.reviewNo,
                        review.customerEntity.customerName.as("customerName"),  // 소비자 아이디
                        review.rating,                                       // 평점
                        review.createdAt.as("createdAt"),                // 리뷰 등록일자
                        creator.creatorName.as("creatorName"),               // 판매자 이름 (creator_name)
                        product.productName,                                // 상품 이름
                        product.productDescription,                         // 상품 설명
                        review.comment,                                     // 리뷰 내용
                        review.reply                                        // 리뷰 답변
                ))
                .groupBy(review.reviewNo, review.customerEntity.customerId, review.rating, review.createdAt,
                        creator.creatorName, product.productName, product.productDescription,
                        review.comment, review.reply);

        this.getQuerydsl().applyPagination(pageable, query);

        List<ReviewListDTO> dtoList = query.fetch();

        // 리뷰 이미지 데이터 조회
        List<Tuple> imageTuples = from(reviewImage)
                .join(reviewImage.reviewEntity, review) // 리뷰 -> 리뷰 이미지
                .select(
                        reviewImage.reviewEntity.reviewNo,
                        Projections.bean(ReviewImageDTO.class,
                                reviewImage.reviewImageNo,
                                reviewImage.reviewImageUrl,
                                reviewImage.reviewImageOrd
                        )
                )
                .fetch();

        // 리뷰 번호를 기준으로 ReviewImageDTO 리스트 매핑
        Map<Long, List<ReviewImageDTO>> reviewImageMap = imageTuples.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(reviewImage.reviewEntity.reviewNo),
                        Collectors.mapping(
                                tuple -> tuple.get(1, ReviewImageDTO.class),
                                Collectors.toList()
                        )
                ));

        // DTO에 리뷰 이미지 리스트 매핑
        dtoList.forEach(dto -> {
            Long reviewNo = dto.getReviewNo();
            dto.setReviewImages(reviewImageMap.getOrDefault(reviewNo, Collections.emptyList()));
        });

        long total = query.fetchCount();

        return PageResponseDTO.<ReviewListDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(total)
                .build();
    }
}
