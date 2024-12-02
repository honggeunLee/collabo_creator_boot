package org.example.collabo_creator_boot.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsWithProductDTO {
    private String categoryName; // 카테고리 이름
    private String productName;  // 상품 이름
    private Long totalSold;      // 판매 수량
    private Long totalRefunded;  // 환불 수량
    private Long totalSales;     // 총 매출액
}
