package org.example.collabo_creator_boot.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private Long productNo;
    private String productName;
    private Integer productPrice;
    private Integer quantity;
    private String productImageUrl;

}