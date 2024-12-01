package org.example.collabo_creator_boot.qna.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.customer.domain.QCustomerEntity;
import org.example.collabo_creator_boot.product.domain.QProductEntity;
import org.example.collabo_creator_boot.qna.domain.QQnAEntity;
import org.example.collabo_creator_boot.qna.domain.QQnAImageEntity;
import org.example.collabo_creator_boot.qna.domain.QnAEntity;
import org.example.collabo_creator_boot.qna.dto.QnAImageDTO;
import org.example.collabo_creator_boot.qna.dto.QnAListDTO;
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
public class QnASearchImpl extends QuerydslRepositorySupport implements QnASearch {

    public QnASearchImpl() {
        super(QnAEntity.class);
    }

    @Override
    public PageResponseDTO<QnAListDTO> qnAListByCreator(String creatorId, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("createdAt").descending());

        QQnAEntity qna = QQnAEntity.qnAEntity;
        QProductEntity product = QProductEntity.productEntity;
        QCustomerEntity customer = QCustomerEntity.customerEntity;
        QQnAImageEntity qnaImage = QQnAImageEntity.qnAImageEntity;

        // QnA 기본 데이터 조회
        JPQLQuery<QnAListDTO> query = from(qna)
                .leftJoin(qna.productEntity, product)                   // QnA -> 상품
                .leftJoin(qna.customerEntity, customer)                 // QnA -> 고객
                .where(qna.creatorEntity.creatorId.eq(creatorId))
                .select(Projections.bean(QnAListDTO.class,
                        qna.qnaNo,
                        product.productName,
                        customer.customerName,
                        qna.question,
                        qna.answer,
                        qna.createdAt
                ))
                .groupBy(qna.qnaNo, product.productName, customer.customerName, qna.question, qna.answer);

        this.getQuerydsl().applyPagination(pageable, query);

        List<QnAListDTO> dtoList = query.fetch();

        // QnA 이미지 데이터 조회
        List<Tuple> imageTuples = from(qnaImage)
                .join(qnaImage.qnaEntity, qna) // QnA -> QnA 이미지
                .select(
                        qnaImage.qnaEntity.qnaNo,
                        Projections.bean(QnAImageDTO.class,
                                qnaImage.qnaImageNo,
                                qnaImage.qnaImageUrl,
                                qnaImage.qnaImageOrd
                        )
                )
                .fetch();

        // QnA 번호를 기준으로 QnAImageDTO 리스트 매핑
        Map<Long, List<QnAImageDTO>> qnaImageMap = imageTuples.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(qnaImage.qnaEntity.qnaNo),
                        Collectors.mapping(
                                tuple -> tuple.get(1, QnAImageDTO.class),
                                Collectors.toList()
                        )
                ));

        // DTO에 QnA 이미지 리스트 매핑
        dtoList.forEach(dto -> {
            Long qnaNo = dto.getQnaNo();
            dto.setQnaImages(qnaImageMap.getOrDefault(qnaNo, Collections.emptyList()));
        });

        long total = query.fetchCount();

        return PageResponseDTO.<QnAListDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(total)
                .build();
    }
}
