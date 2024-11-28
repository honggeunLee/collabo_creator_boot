package org.example.collabo_creator_boot.order.domain;

import jakarta.persistence.*;
import org.example.collabo_creator_boot.common.BasicEntity;
import org.example.collabo_creator_boot.product.domain.ProductEntity;

@Entity
@Table(name = "order_item")
public class OrderItemEntity extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_item_no", nullable = false)
    private Long orderItemNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", referencedColumnName = "order_no")
    private OrdersEntity orderNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", referencedColumnName = "product_no")
    private ProductEntity productNo;

    @Column(name = "quantity", nullable = false)
    private int quantity;

}
