package org.example.collabo_creator_boot.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.collabo_creator_boot.product.domain.ProductStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReadDTO {
    // Product data
    private Long productNo;
    private String productName;
    private String productDescription;
    private Integer productPrice;
    private Integer stock;
    private String productStatus;

    @JsonProperty("productStatus")
    public String getProductStatusDescription() {
        return ProductStatus.fromDbValue(productStatus).getDescription();
    }

    // Category data
    private String categoryName;

    // Add categoryNo for updates
    private Long categoryNo;

    // Review data
    private int rating;
    private String comment;

    // Image data (multiple images)
    private List<ProductImageDTO> productImages; // 다수 이미지 처리
}
