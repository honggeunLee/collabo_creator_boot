package org.example.collabo_creator_boot.order.controller;

import lombok.RequiredArgsConstructor;
import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.order.dto.OrderDetailDTO;
import org.example.collabo_creator_boot.order.dto.OrderListDTO;
import org.example.collabo_creator_boot.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/list")
    public ResponseEntity<PageResponseDTO<OrderListDTO>> getOrderList(
            @CookieValue(value = "creatorId", required = false) String creatorId,
            @ModelAttribute PageRequestDTO pageRequestDTO) {

        if (creatorId == null || creatorId.isEmpty()) {
            throw new IllegalArgumentException("Creator ID is missing.");
        }

        PageResponseDTO<OrderListDTO> response = orderService.getOrderListByCreator(creatorId, pageRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderNo}")
    public ResponseEntity<OrderDetailDTO> getOrderDetail(
            @CookieValue(value = "creatorId", required = false) String creatorId,
            @PathVariable Long orderNo) {

        if (creatorId == null || creatorId.isEmpty()) {
            throw new IllegalArgumentException("Creator ID is missing.");
        }

        OrderDetailDTO orderDetail = orderService.getOrderDetailByCreator(creatorId, orderNo);
        return ResponseEntity.ok(orderDetail);
    }
}
