package org.example.collabo_creator_boot.order.domain;

import jakarta.persistence.*;
import org.example.collabo_creator_boot.product.domain.ProductEntity;

@Entity
@Table(name = "order_item")
public class OrderItemEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_item_no", nullable = false)
    private Long orderItemNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", referencedColumnName = "order_no")
    private OrdersEntity ordersEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", referencedColumnName = "product_no")
    private ProductEntity productEntity;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}
