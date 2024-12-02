package org.example.collabo_creator_boot.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDTO {
    private String month;         // 월
    private Long totalSales;      // 총 매출액
    private Long orderCount;      // 주문 수
}
