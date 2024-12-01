package org.example.collabo_creator_boot.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.collabo_creator_boot.product.domain.ProductStatus;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductListDTO {

    private Long productNo;
    private String productName;
    private Integer productPrice;
    private Integer stock;
    private String productStatus;
    private Long categoryNo;
    private String categoryName;

    @JsonProperty("productStatus")
    public String getProductStatusDescription() {
        return ProductStatus.fromDbValue(productStatus).getDescription();
    }

    private List<String> productImageUrl;

    private LocalDateTime createdAt;

    @JsonProperty("createdAt")
    public String getFormattedCreatedAt() {
        if (createdAt != null) {
            return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return null;
    }

}
