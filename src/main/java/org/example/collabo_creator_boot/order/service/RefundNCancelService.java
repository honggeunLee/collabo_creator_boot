package org.example.collabo_creator_boot.order.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.collabo_creator_boot.order.domain.RefundNCancelStatus;
import org.example.collabo_creator_boot.order.dto.RefundNCancelDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RefundNCancelService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RefundNCancelDTO> getRefundAndCancelStatistics(String creatorId, LocalDateTime startDate, LocalDateTime endDate) {
        String jpql = "SELECT r.status, COUNT(r) " +
                "FROM RefundNCancelEntity r " +
                "JOIN r.ordersEntity o " +
                "JOIN o.orderItems oi " +
                "JOIN oi.productEntity p " +
                "JOIN p.creatorEntity c " +
                "WHERE c.creatorId = :creatorId " +
                "AND r.createdAt BETWEEN :startDate AND :endDate " +
                "GROUP BY r.status";

        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class)
                .setParameter("creatorId", creatorId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate);

        List<Object[]> results = query.getResultList();

        // 전체 주문 개수 계산
        long totalOrders = results.stream()
                .mapToLong(row -> (long) row[1])
                .sum();

        // 결과 매핑 및 DTO 반환
        return results.stream()
                .map(row -> {
                    int status = (int) row[0];
                    long count = (long) row[1];
                    String description = RefundNCancelStatus.fromDbValue(String.valueOf(status)).getDescription();
                    double percentage = totalOrders > 0 ? ((double) count / totalOrders) * 100 : 0;
                    return RefundNCancelDTO.builder()
                            .status(description)
                            .count(count)
                            .percentage(Math.round(percentage * 100.0) / 100.0) // 소수점 2자리 반올림
                            .build();
                })
                .collect(Collectors.toList());
    }
}