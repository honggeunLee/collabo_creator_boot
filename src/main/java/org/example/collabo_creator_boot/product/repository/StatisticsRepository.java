package org.example.collabo_creator_boot.product.repository;

import org.example.collabo_creator_boot.product.domain.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<ProductEntity, Long> {

    @Query("""
        SELECT p, c, r, i 
        FROM ProductEntity p
        LEFT JOIN p.categoryEntity c
        LEFT JOIN ReviewEntity r ON r.productEntity.productNo = p.productNo
        LEFT JOIN ProductImageEntity i ON i.productEntity.productNo = p.productNo
        WHERE p.productNo = :productNo
    """)
    List<Object[]> getStatistics(@Param("productNo") Long productNo);


}
