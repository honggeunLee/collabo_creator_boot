package org.example.collabo_creator_boot.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.collabo_creator_boot.order.domain.OrderStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListDTO {
    private Long orderNo;
    private LocalDateTime orderDate;
    private String customerName;
    private String customerPhone;
    private String customerAddr;
    private String customerAddrDetail;
    private List<OrderItemDTO> orderItems;
    private Integer totalPrice;
    private String orderStatus; // 데이터베이스 값 (숫자)

    @JsonProperty("orderDate")
    public String getFormattedCreatedAt() {
        if (orderDate != null) {
            return orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return null;
    }

    @JsonProperty("orderStatus") // JSON 응답에서 문자열 상태 반환
    public String getOrderStatusDescription() {
        try {
            return OrderStatus.fromDbValue(orderStatus).getDescription();
        } catch (IllegalArgumentException e) {
            return "알 수 없음";
        }
    }
}
