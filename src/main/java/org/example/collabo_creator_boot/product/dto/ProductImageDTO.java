package org.example.collabo_creator_boot.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProductImageDTO {
    private String productImageUrl;
    private int productImageOrd; // 이미지 순서
}
