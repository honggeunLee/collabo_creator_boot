package org.example.collabo_creator_boot.order.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.collabo_creator_boot.common.BasicEntity;
import org.example.collabo_creator_boot.customer.domain.CustomerEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "orders")
public class OrdersEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_no", nullable = false)
    private Long orderNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private CustomerEntity customerEntity;

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "customer_address", nullable = false)
    private String customerAddress;

    @Column(name = "customer_addr_detail", nullable = false)
    private String customerAddrDetail;

    @OneToMany(mappedBy = "ordersEntity", fetch = FetchType.LAZY)
    private Set<OrderItemEntity> orderItems = new HashSet<>();
}