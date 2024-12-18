package org.example.collabo_creator_boot.category.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.collabo_creator_boot.common.BasicEntity;
import org.example.collabo_creator_boot.creator.domain.CreatorEntity;

@Entity
@Getter
@Table(name = "category")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_no", nullable = false)
    private Long categoryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "creator_id")
    private CreatorEntity creatorEntity;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

}