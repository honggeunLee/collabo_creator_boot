package org.example.collabo_creator_boot.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.collabo_creator_boot.category.domain.CategoryEntity;
import org.example.collabo_creator_boot.common.BasicEntity;
import org.example.collabo_creator_boot.creator.domain.CreatorEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no")
    private Long productNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "creator_id")
    private CreatorEntity creatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no", referencedColumnName = "category_no")
    private CategoryEntity categoryNo;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_price", nullable = false)
    private String productPrice;

    @Column(name = "stock")
    private String stock;

    @Column(name = "product_status")
    private String productStatus;

}
