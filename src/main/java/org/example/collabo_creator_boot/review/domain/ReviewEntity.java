package org.example.collabo_creator_boot.review.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.collabo_creator_boot.common.BasicEntity;
import org.example.collabo_creator_boot.customer.domain.CustomerEntity;
import org.example.collabo_creator_boot.product.domain.ProductEntity;

@Entity
@Getter
@Table(name = "review")
public class ReviewEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_no", nullable = false)
    private Long reviewNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", referencedColumnName = "product_no")
    private ProductEntity productNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private CustomerEntity customerId;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "reply", nullable = false)
    private String reply;

    @Column(name = "review_like", nullable = false)
    private int reviewLike;
}
