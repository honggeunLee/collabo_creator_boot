package org.example.collabo_creator_boot.order.service;

import lombok.RequiredArgsConstructor;
import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.order.dto.OrderDetailDTO;
import org.example.collabo_creator_boot.order.dto.OrderItemDTO;
import org.example.collabo_creator_boot.order.dto.OrderListDTO;
import org.example.collabo_creator_boot.order.repository.OrderRepository;
import org.example.collabo_creator_boot.order.repository.search.OrderSearch;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderSearch orderSearch;
    private final OrderRepository orderRepository;

    public PageResponseDTO<OrderListDTO> getOrderList(PageRequestDTO pageRequestDTO) {
        return orderSearch.orderList(pageRequestDTO);
    }

    public OrderDetailDTO getOrderDetail(Long orderNo) {
        OrderDetailDTO orderDetail = orderRepository.findOrderDetail(orderNo);
        if (orderDetail == null) {
            throw new IllegalArgumentException("Order not found for orderNo: " + orderNo);
        }
        List<OrderItemDTO> orderItems = orderRepository.findOrderItems(orderNo);
        orderDetail.setOrderItems(orderItems);
        return orderDetail;
    }
}
