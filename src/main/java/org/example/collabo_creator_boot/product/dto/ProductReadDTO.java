package org.example.collabo_creator_boot.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.example.collabo_creator_boot.product.domain.ProductStatus;

import java.util.List;

@Data
@Builder
public class ProductReadDTO {
    //product data
    private Long productNo;
    private String productName;
    private String productDescription;
    private Integer productPrice;
    private String stock;
    private String productStatus;

    @JsonProperty("productStatus")
    public String getProductStatusDescription() {
        return ProductStatus.fromDbValue(productStatus).getDescription();
    }

    //category data
    private String categoryName;

    //review data
    private int rating;
    private String comment;
    private int reviewLike;

    //img data
    private String productImageUrl;

}