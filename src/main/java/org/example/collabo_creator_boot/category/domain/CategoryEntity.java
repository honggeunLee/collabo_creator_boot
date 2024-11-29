package org.example.collabo_creator_boot.category.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.collabo_creator_boot.common.BasicEntity;
import org.example.collabo_creator_boot.creator.domain.CreatorEntity;

@Entity
@Getter
@Table(name = "category")
public class CategoryEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_no", nullable = false)
    private Long categoryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "creator_id")
    private CreatorEntity creator_id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

}
