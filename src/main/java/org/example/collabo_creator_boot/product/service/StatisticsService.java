package org.example.collabo_creator_boot.product.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.collabo_creator_boot.product.dto.StatisticsDTO;
import org.example.collabo_creator_boot.product.dto.StatisticsWithProductDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<StatisticsDTO> getSalesStatistics(String creatorId, LocalDateTime startDate, LocalDateTime endDate) {

        String queryStr = """
            SELECT
                FUNCTION('MONTHNAME', o.createdAt) AS month,
                SUM(o.totalPrice) AS totalSales,
                COUNT(o.totalPrice) AS orderCount
            FROM
                OrdersEntity o
            JOIN
                o.orderItems oi
            JOIN
                oi.productEntity p
            WHERE
                o.createdAt BETWEEN :startDate AND :endDate
                AND p.creatorEntity.creatorId = :creatorId
            GROUP BY
                FUNCTION('MONTH', o.createdAt), FUNCTION('MONTHNAME', o.createdAt)
            ORDER BY
                FUNCTION('MONTH', o.createdAt)
        """;

        TypedQuery<Object[]> query = entityManager.createQuery(queryStr, Object[].class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("creatorId", creatorId);

        List<Object[]> result = query.getResultList();

        return result.stream()
                .map(row -> StatisticsDTO.builder()
                        .month((String) row[0])        // 월 이름
                        .totalSales((Long) row[1])    // totalPrice 합계
                        .orderCount((Long) row[2])    // order 수
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<StatisticsWithProductDTO> getProductSalesStatistics(
            String creatorId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Long category,
            String searchTerm
    ) {
        StringBuilder queryBuilder = new StringBuilder("""
        SELECT 
            c.categoryName AS categoryName,
            p.productName AS productName,
            SUM(oi.quantity) AS totalSold,
            SUM(CASE WHEN rc.status = 1 THEN oi.quantity ELSE 0 END) AS totalRefunded,
            SUM(oi.quantity * p.productPrice) AS totalSales
        FROM OrdersEntity o
        JOIN o.orderItems oi
        JOIN oi.productEntity p
        JOIN p.categoryEntity c
        LEFT JOIN RefundNCancelEntity rc ON rc.ordersEntity = o
        WHERE o.createdAt BETWEEN :startDate AND :endDate
        AND p.creatorEntity.creatorId = :creatorId
    """);

        // 동적 조건 추가
        if (category != null) {
            queryBuilder.append(" AND c.categoryNo = :category");
        }
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            queryBuilder.append(" AND LOWER(p.productName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))");
        }

        queryBuilder.append("""
        GROUP BY c.categoryName, p.productName
        ORDER BY c.categoryName, p.productName
    """);

        TypedQuery<Object[]> query = entityManager.createQuery(queryBuilder.toString(), Object[].class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("creatorId", creatorId);

        // 선택적 파라미터 설정
        if (category != null) {
            query.setParameter("category", category);
        }
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            query.setParameter("searchTerm", searchTerm.trim());
        }

        List<Object[]> results = query.getResultList();

        return results.stream()
                .map(row -> StatisticsWithProductDTO.builder()
                        .categoryName((String) row[0])
                        .productName((String) row[1])
                        .totalSold((Long) row[2])
                        .totalRefunded((Long) row[3])
                        .totalSales((Long) row[4])
                        .build())
                .collect(Collectors.toList());
    }

}
