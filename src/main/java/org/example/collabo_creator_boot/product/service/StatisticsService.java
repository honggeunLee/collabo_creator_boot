package org.example.collabo_creator_boot.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.collabo_creator_boot.category.domain.CategoryEntity;
import org.example.collabo_creator_boot.product.domain.ProductEntity;
import org.example.collabo_creator_boot.product.domain.ProductImageEntity;
import org.example.collabo_creator_boot.product.dto.ProductReadDTO;
import org.example.collabo_creator_boot.product.dto.StatisticsDTO;
import org.example.collabo_creator_boot.product.repository.StatisticsRepository;
import org.example.collabo_creator_boot.review.domain.ReviewEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

//    public StatisticsDTO getStatistics() {
//        List<Object[]> result = statisticsRepository.getStatistics();
//
//        if (result.isEmpty()) {
//            throw new RuntimeException("Not valueable creatorId");
//        }
//
//        ProductEntity product = (ProductEntity) result.get(0)[0];
//        CategoryEntity category = (CategoryEntity) result.get(0)[1];
//
//        List<ReviewEntity> reviews = result.stream()
//                .map(arr -> (ReviewEntity) arr[2])
//                .filter(review -> review != null)
//                .collect(Collectors.toList());
//
//        List<ProductImageEntity> images = result.stream()
//                .map(arr -> (ProductImageEntity) arr[3])
//                .filter(image -> image != null)
//                .collect(Collectors.toList());
//
//        //리뷰 평점 계산
//        int averageRating = reviews.isEmpty() ? 0 : (int) reviews
//                .stream()
//                .mapToInt(ReviewEntity::getRating)
//                .average()
//                .orElse(0);
//
//        String comment = reviews.isEmpty() ? null : reviews
//                .get(0)
//                .getComment();
//
//        List<String> imageUrls = images.stream()
//                .map(ProductImageEntity::getProductImageUrl)
//                .collect(Collectors.toList());
//
//        return ProductReadDTO.builder()
//                .productNo(product.getProductNo())
//                .productName(product.getProductName())
//                .productDescription(product.getProductDescription())
//                .productPrice(product.getProductPrice())
//                .stock(product.getStock())
//                .productStatus(product.getProductStatus())
//                .categoryName(category.getCategoryName())
//                .rating(averageRating)
//                .productImageUrl(imageUrls.isEmpty() ? null : imageUrls.get(0))
//                .build();
//    }

}
