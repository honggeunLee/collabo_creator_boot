package org.example.collabo_creator_boot.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.collabo_creator_boot.common.BasicEntity;

@Entity
@Getter

@Table(name = "product_image")
public class ProductImageEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_no")
    private int productImageNo;

    @Column(name = "product_image_url")
    private String productImageUrl;

    @Column(name = "product_image_ord")
    private int productImageOrd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", referencedColumnName = "product_no")
    private ProductEntity productNo;

}