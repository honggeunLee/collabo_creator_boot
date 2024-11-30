package org.example.collabo_creator_boot.product.service;

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
import org.example.collabo_creator_boot.product.dto.ProductListDTO;
import org.example.collabo_creator_boot.product.dto.ProductReadDTO;
import org.example.collabo_creator_boot.product.dto.ProductRegisterDTO;
import org.example.collabo_creator_boot.product.repository.ProductRepository;
import org.example.collabo_creator_boot.review.domain.ReviewEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CreatorRepository creatorRepository;

    public PageResponseDTO<ProductListDTO> getProductList(PageRequestDTO pageRequestDTO){
        return productRepository.productList(pageRequestDTO);
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

        //리뷰 평점 계산
        int averageRating = reviews.isEmpty() ? 0 : (int) reviews
                .stream()
                .mapToInt(ReviewEntity::getRating)
                .average()
                .orElse(0);

        String comment = reviews.isEmpty() ? null : reviews
                .get(0)
                .getComment();

        List<String> imageUrls = images.stream()
                .map(ProductImageEntity::getProductImageUrl)
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
                .productImageUrl(imageUrls.isEmpty() ? null : imageUrls.get(0))
                .build();
    }

    public Long registerProduct(ProductRegisterDTO productRegisterDTO) {

        CategoryEntity category = categoryRepository.findById(productRegisterDTO.getCategoryNo())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        CreatorEntity creator = creatorRepository.findById(productRegisterDTO.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        ProductEntity product = addProductEntity(productRegisterDTO, category, creator);

        // 이미지 데이터 처리
        if (productRegisterDTO.getProductImages() != null && !productRegisterDTO.getProductImages().isEmpty()) {
            productRegisterDTO.getProductImages().forEach(imageUrl -> {
                ProductImageEntity productImage = ProductImageEntity.builder()
                        .productImageUrl(imageUrl)
                        .build();
                product.addProductImage(productImage); // 연관 관계 설정
            });
        }


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


}
