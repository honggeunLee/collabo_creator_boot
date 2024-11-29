package org.example.collabo_creator_boot.product.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.example.collabo_creator_boot.category.domain.QCategoryEntity;
import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.creator.domain.QCreatorEntity;
import org.example.collabo_creator_boot.product.domain.ProductEntity;
import org.example.collabo_creator_boot.product.domain.QProductEntity;
import org.example.collabo_creator_boot.product.domain.QProductImageEntity;
import org.example.collabo_creator_boot.product.dto.ProductListDTO;
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
@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl() {super(ProductEntity.class);}

    @Override
    public PageResponseDTO<ProductListDTO> productList(PageRequestDTO pageRequestDTO){

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("productNo").descending());

        QProductEntity product = QProductEntity.productEntity;
        QProductImageEntity productImage = QProductImageEntity.productImageEntity;
        QCategoryEntity category = QCategoryEntity.categoryEntity;
        QCreatorEntity creator = QCreatorEntity.creatorEntity;

        JPQLQuery<ProductEntity> query = from(product);
        query.leftJoin(productImage).on(product.productNo.eq(productImage.productEntity.productNo));
        query.leftJoin(category).on(product.categoryEntity.categoryNo.eq(category.categoryNo));
        query.leftJoin(creator).on(product.creatorEntity.creatorId.eq(creator.creatorId));

        this.getQuerydsl().applyPagination(pageable,query);

        JPQLQuery<ProductListDTO> dtojpqlQuery = query.select(
                Projections.bean(ProductListDTO.class,
                        product.productNo,
                        product.productName,
                        product.productPrice,
                        product.stock,
                        product.productStatus.stringValue().as("productStatus"),
                        category.categoryNo,
                        category.categoryName,
                        creator.creatorName
                )
        );

        List<ProductListDTO> dtoList = dtojpqlQuery.fetch();

        // 이미지 데이터 조회
        List<Tuple> imageTuples = from(productImage)
                .select(
                        productImage.productEntity.productNo,
                        productImage.productImageUrl
                )
                .fetch();

        Map<Long, List<String>> productImageMap = imageTuples.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(productImage.productEntity.productNo),
                        Collectors.mapping(
                                tuple -> tuple.get(productImage.productImageUrl),
                                Collectors.toList()
                        )
                ));

        // DTO에 이미지 매핑
        dtoList.forEach(dto -> {
            Long productNo = dto.getProductNo();
            dto.setProductImageUrl(productImageMap.getOrDefault(productNo, Collections.emptyList()));
        });

        long total = query.fetchCount();

        return PageResponseDTO.<ProductListDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(total)
                .build();
    }


}
