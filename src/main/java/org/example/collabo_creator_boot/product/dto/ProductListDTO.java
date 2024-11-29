package org.example.collabo_creator_boot.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.collabo_creator_boot.product.domain.ProductStatus;

import java.util.List;

@Data
public class ProductListDTO {

    private Long productNo;
    private String productName;
    private Integer productPrice;
    private String stock;
    private String productStatus;

    @JsonProperty("productStatus")
    public String getProductStatusDescription() {
        return ProductStatus.fromDbValue(productStatus).getDescription();
    }
    private List<String> productImageUrl;

}
