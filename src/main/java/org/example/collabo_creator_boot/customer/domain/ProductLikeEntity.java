package org.example.collabo_creator_boot.customer.domain;

import jakarta.persistence.*;
import org.example.collabo_creator_boot.product.domain.ProductEntity;

@Entity
@Table(name = "product_like")
public class ProductLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_like_no", nullable = false)
    private Long productLikeNo;

    @Column(name = "like_status")
    private Boolean likeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private CustomerEntity customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", referencedColumnName = "product_no")
    private ProductEntity productNo;
}
