package org.example.collabo_creator_boot.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefundNCancelDTO {
    private String status; // "환불", "취소", "발송 처리"
    private long count;    // 해당 상태의 주문 수
    private double percentage; // 전체 대비 비율(%)
}