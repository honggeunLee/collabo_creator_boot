package org.example.collabo_creator_boot.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.collabo_creator_boot.order.domain.OrderStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class OrderDetailDTO {
    private Long orderNo;
    private LocalDateTime orderDate;
    private String orderStatus;

    // 주문자 정보
    private String customerName;
    private String customerPhone;
    private String customerAddr;
    private String customerAddrDetail;

    // 주문 상품
    private List<OrderItemDTO> orderItems;

    public OrderDetailDTO(Long orderNo, LocalDateTime orderDate, String orderStatus,
                          String customerName, String customerPhone, String customerAddr, String customerAddrDetail) {
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddr = customerAddr;
        this.customerAddrDetail = customerAddrDetail;
    }

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
