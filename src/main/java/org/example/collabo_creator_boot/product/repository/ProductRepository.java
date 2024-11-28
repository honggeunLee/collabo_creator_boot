package org.example.collabo_creator_boot.product.repository;

import org.example.collabo_creator_boot.product.domain.ProductEntity;
import org.example.collabo_creator_boot.product.repository.search.ProductSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,String>, ProductSearch {

    @Query("""
        SELECT p, c, r, i 
        FROM ProductEntity p
        LEFT JOIN p.categoryNo c
        LEFT JOIN ReviewEntity r ON r.productNo = p
        LEFT JOIN ProductImageEntity i ON i.productNo = p
        WHERE p.productNo = :productNo
    """)
    List<Object[]> readProductDetails(@Param("productNo") Long productNo);

}
