package org.example.collabo_creator_boot.product.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.collabo_creator_boot.category.domain.CategoryEntity;
import org.example.collabo_creator_boot.category.repository.CategoryRepository;
import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.creator.domain.CreatorEntity;
import org.example.collabo_creator_boot.creator.repository.CreatorRepository;
import org.example.collabo_creator_boot.product.domain.ProductEntity;
import org.example.collabo_creator_boot.product.domain.ProductImageEntity;
import org.example.collabo_creator_boot.product.dto.ProductImageDTO;
import org.example.collabo_creator_boot.product.dto.ProductListDTO;
import org.example.collabo_creator_boot.product.dto.ProductReadDTO;
import org.example.collabo_creator_boot.product.dto.ProductRegisterDTO;
import org.example.collabo_creator_boot.product.repository.ProductRepository;
import org.example.collabo_creator_boot.review.domain.ReviewEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CreatorRepository creatorRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public PageResponseDTO<ProductListDTO> getCreatorProductList(
            String creatorId,
            PageRequestDTO pageRequestDTO,
            String searchQuery,
            String status,
            Long categoryNo) {

        // 필터 조건에 따라 데이터 조회
        return productRepository.productListByCreator(creatorId, pageRequestDTO, searchQuery, status, categoryNo);
    }


    public ProductReadDTO readProductDetails(Long productNo) {
        List<Object[]> result = productRepository.readProductDetails(productNo);

        if (result.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        ProductEntity product = (ProductEntity) result.get(0)[0];
        CategoryEntity category = (CategoryEntity) result.get(0)[1];

        List<ReviewEntity> reviews = result.stream()
                .map(arr -> (ReviewEntity) arr[2])
                .filter(review -> review != null)
                .collect(Collectors.toList());

        List<ProductImageEntity> images = result.stream()
                .map(arr -> (ProductImageEntity) arr[3])
                .filter(image -> image != null)
                .collect(Collectors.toList());

        // 리뷰 평점 계산
        int averageRating = reviews.isEmpty() ? 0 : (int) reviews.stream()
                .mapToInt(ReviewEntity::getRating)
                .average()
                .orElse(0);

        String comment = reviews.isEmpty() ? null : reviews.get(0).getComment();

        // 중복 이미지 제거 및 DTO로 변환
        List<ProductImageDTO> imageDTOs = images.stream()
                .map(image -> ProductImageDTO.builder()
                        .productImageUrl(image.getProductImageUrl())
                        .productImageOrd(image.getProductImageOrd())
                        .build())
                .collect(Collectors.toMap(
                        ProductImageDTO::getProductImageUrl, // 중복 제거 기준
                        Function.identity(),                // 유지할 값
                        (existing, replacement) -> existing // 중복 시 기존 값 유지
                ))
                .values()
                .stream()
                .sorted(Comparator.comparingInt(ProductImageDTO::getProductImageOrd)) // 순서 정렬
                .collect(Collectors.toList());

        return ProductReadDTO.builder()
                .productNo(product.getProductNo())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .productPrice(product.getProductPrice())
                .stock(product.getStock())
                .productStatus(product.getProductStatus())
                .categoryName(category.getCategoryName())
                .rating(averageRating)
                .comment(comment)
                .productImages(imageDTOs) // 중복 제거된 이미지 배열 추가
                .build();
    }



    public void updateProduct(String creatorId, Long productNo, ProductReadDTO productReadDTO) {
        // 로그
        log.info("Updating product: {}, Creator ID: {}", productNo, creatorId);
        log.info("DTO received: {}", productReadDTO);

        // 기존 상품 조회
        ProductEntity existingProduct = productRepository.findById(productNo)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + productNo));

        log.info("Existing Product: {}", existingProduct);

        // 카테고리 조회
        CategoryEntity categoryEntity = categoryRepository.findById(productReadDTO.getCategoryNo())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다: " + productReadDTO.getCategoryNo()));

        log.info("Category Entity: {}", categoryEntity);

        // 이미지 데이터 변환
        List<ProductImageEntity> updatedImages = productReadDTO.getProductImages().stream()
                .map(imageDTO -> ProductImageEntity.builder()
                        .productImageUrl(imageDTO.getProductImageUrl())
                        .productImageOrd(imageDTO.getProductImageOrd())
                        .productEntity(existingProduct) // 관계 설정
                        .build())
                .toList();

        log.info("Updated Images: {}", updatedImages);

        // 상품 업데이트
        ProductEntity updatedProduct = ProductEntity.builder()
                .productNo(existingProduct.getProductNo())
                .productName(productReadDTO.getProductName())
                .productDescription(productReadDTO.getProductDescription())
                .productPrice(productReadDTO.getProductPrice())
                .stock(productReadDTO.getStock())
                .productStatus(productReadDTO.getProductStatus())
                .creatorEntity(existingProduct.getCreatorEntity())
                .categoryEntity(categoryEntity)
                .productImages(updatedImages) // 이미지 리스트 업데이트
                .build();

        productRepository.save(updatedProduct);
        log.info("Product updated successfully with images.");
    }


    public Long registerProduct(ProductRegisterDTO productRegisterDTO) {
        // 카테고리 및 크리에이터 조회
        CategoryEntity category = categoryRepository.findById(productRegisterDTO.getCategoryNo())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        CreatorEntity creator = creatorRepository.findById(productRegisterDTO.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        // ProductEntity 생성
        ProductEntity product = addProductEntity(productRegisterDTO, category, creator);

        // 이미지 데이터 처리
        if (productRegisterDTO.getProductImages() != null && !productRegisterDTO.getProductImages().isEmpty()) {
            List<ProductImageEntity> productImages = productRegisterDTO.getProductImages().stream()
                    .map(image -> ProductImageEntity.builder()
                            .productImageUrl(image.getProductImageUrl())
                            .productImageOrd(image.getProductImageOrd()) // 순서 설정
                            .build())
                    .collect(Collectors.toList());

            productImages.forEach(product::addProductImage); // 연관 관계 설정
        }

        // 상품 저장
        productRepository.save(product);

        return product.getProductNo();
    }

    private ProductEntity addProductEntity(ProductRegisterDTO dto, CategoryEntity category, CreatorEntity creator) {
        return ProductEntity.builder()
                .productName(dto.getProductName())
                .productDescription(dto.getProductDescription())
                .productPrice(dto.getProductPrice())
                .stock(dto.getStock())
                .categoryEntity(category)
                .creatorEntity(creator)
                .productStatus(dto.getProductStatus())
                .build();
    }

    public List<ProductImageDTO> saveImages(Long productNo, List<ProductImageDTO> imageDTOs) {
        // `ProductEntity` 조회
        ProductEntity product = entityManager.find(ProductEntity.class, productNo);
        if (product == null) {
            throw new IllegalArgumentException("Invalid product ID: " + productNo);
        }

        List<ProductImageDTO> savedImageDTOs = new ArrayList<>();

        for (ProductImageDTO dto : imageDTOs) {
            // `ProductImageEntity` 생성
            ProductImageEntity imageEntity = ProductImageEntity.builder()
                    .productImageUrl(dto.getProductImageUrl())
                    .productImageOrd(dto.getProductImageOrd())
                    .productEntity(product)
                    .build();

            // 엔티티를 영속화
            entityManager.persist(imageEntity);

            // DTO로 변환하여 응답 리스트에 추가
            savedImageDTOs.add(ProductImageDTO.builder()
                    .productImageUrl(imageEntity.getProductImageUrl())
                    .productImageOrd(imageEntity.getProductImageOrd())
                    .build());
        }

        return savedImageDTOs;
    }

    public void deleteProductImage(Long imageId) {
        ProductImageEntity imageEntity = ProductImageEntity.builder()
                .productImageNo(imageId.intValue()) // ID로만 삭제 작업
                .build();

        ProductImageEntity managedEntity = entityManager.merge(imageEntity);
        entityManager.remove(managedEntity);
    }


}
