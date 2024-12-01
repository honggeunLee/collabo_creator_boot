package org.example.collabo_creator_boot.order.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.customer.domain.QCustomerEntity;
import org.example.collabo_creator_boot.order.domain.OrdersEntity;
import org.example.collabo_creator_boot.order.domain.QOrderItemEntity;
import org.example.collabo_creator_boot.order.domain.QOrdersEntity;
import org.example.collabo_creator_boot.order.dto.OrderItemDTO;
import org.example.collabo_creator_boot.order.dto.OrderListDTO;
import org.example.collabo_creator_boot.product.domain.QProductEntity;
import org.example.collabo_creator_boot.product.domain.QProductImageEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class OrderSearchImpl extends QuerydslRepositorySupport implements OrderSearch {

    public OrderSearchImpl() {
        super(OrdersEntity.class);
    }

    @Override
    public PageResponseDTO<OrderListDTO> orderListByCreator(String creatorId, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("orderNo").descending());

        QOrdersEntity orders = QOrdersEntity.ordersEntity;
        QCustomerEntity customer = QCustomerEntity.customerEntity;
        QOrderItemEntity orderItem = QOrderItemEntity.orderItemEntity;

        // 기본 주문 데이터 조회
        JPQLQuery<OrderListDTO> query = from(orders)
                .leftJoin(orders.customerEntity, customer)
                .where(orders.creatorEntity.creatorId.eq(creatorId)) // 필터링 조건 추가
                .select(Projections.bean(OrderListDTO.class,
                        orders.orderNo,
                        orders.createdAt.as("orderDate"),
                        customer.customerName,
                        customer.customerPhone,
                        customer.customerAddr,
                        customer.customerAddrDetail,
                        orders.totalPrice,
                        orders.status.stringValue().as("orderStatus")
                ))
                .groupBy(orders.orderNo, customer.customerName, customer.customerPhone, customer.customerAddr, customer.customerAddrDetail, orders.createdAt, orders.totalPrice, orders.status);

        this.getQuerydsl().applyPagination(pageable, query);

        List<OrderListDTO> dtoList = query.fetch();

        // 주문 상품 데이터 조회
        List<Tuple> itemTuples = from(orderItem)
                .leftJoin(orderItem.productEntity, QProductEntity.productEntity)
                .leftJoin(QProductEntity.productEntity.productImages, QProductImageEntity.productImageEntity)
                .on(QProductImageEntity.productImageEntity.productImageOrd.eq(0))
                .select(
                        orderItem.ordersEntity.orderNo,
                        Projections.bean(OrderItemDTO.class,
                                QProductEntity.productEntity.productNo,
                                QProductEntity.productEntity.productName,
                                QProductEntity.productEntity.productPrice,
                                orderItem.quantity,
                                QProductImageEntity.productImageEntity.productImageUrl
                        )
                )
                .fetch();

        Map<Long, List<OrderItemDTO>> orderItemMap = itemTuples.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(orderItem.ordersEntity.orderNo),
                        Collectors.mapping(
                                tuple -> tuple.get(1, OrderItemDTO.class),
                                Collectors.toList()
                        )
                ));

        dtoList.forEach(dto -> {
            Long orderNo = dto.getOrderNo();
            dto.setOrderItems(orderItemMap.getOrDefault(orderNo, Collections.emptyList()));
        });

        long total = query.fetchCount();

        return PageResponseDTO.<OrderListDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(total)
                .build();
    }


}
