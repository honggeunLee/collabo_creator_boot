package org.example.collabo_creator_boot.order.repository;

import org.example.collabo_creator_boot.order.domain.OrdersEntity;
import org.example.collabo_creator_boot.order.dto.OrderDetailDTO;
import org.example.collabo_creator_boot.order.dto.OrderItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {

    @Query("SELECT new org.example.collabo_creator_boot.order.dto.OrderDetailDTO(" +
            "o.orderNo, o.createdAt, o.status, " +
            "c.customerName, c.customerPhone, o.customerAddress, o.customerAddrDetail) " +
            "FROM OrdersEntity o " +
            "JOIN o.customerEntity c " +
            "WHERE o.orderNo = :orderNo")
    OrderDetailDTO findOrderDetail(@Param("orderNo") Long orderNo);

    @Query("SELECT new org.example.collabo_creator_boot.order.dto.OrderItemDTO(" +
            "oi.productEntity.productNo, oi.productEntity.productName, oi.productEntity.productPrice, " +
            "oi.quantity, pi.productImageUrl) " +
            "FROM OrderItemEntity oi " +
            "JOIN oi.productEntity.productImages pi ON pi.productImageOrd = 0 " +
            "WHERE oi.ordersEntity.orderNo = :orderNo")
    List<OrderItemDTO> findOrderItems(@Param("orderNo") Long orderNo);
}