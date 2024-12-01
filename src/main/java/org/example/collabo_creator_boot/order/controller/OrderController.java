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
    public ResponseEntity<PageResponseDTO<OrderListDTO>> getOrderList(@ModelAttribute PageRequestDTO pageRequestDTO) {
        PageResponseDTO<OrderListDTO> response = orderService.getOrderList(pageRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderNo}")
    public ResponseEntity<OrderDetailDTO> getOrderDetail(@PathVariable Long orderNo) {
        OrderDetailDTO orderDetail = orderService.getOrderDetail(orderNo);
        return ResponseEntity.ok(orderDetail);
    }
}
