package org.example.collabo_creator_boot.product.controller;

import lombok.extern.log4j.Log4j2;
import org.example.collabo_creator_boot.product.dto.StatisticsDTO;
import org.example.collabo_creator_boot.product.dto.StatisticsWithProductDTO;
import org.example.collabo_creator_boot.product.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("")
    public ResponseEntity<List<StatisticsDTO>> getStatistics(
            @RequestParam("creatorId") String creatorId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        // 문자열 값을 트리밍하고 LocalDateTime으로 변환
        LocalDateTime parsedStartDate = LocalDateTime.parse(startDate.trim());
        LocalDateTime parsedEndDate = LocalDateTime.parse(endDate.trim());

        List<StatisticsDTO> statistics = statisticsService.getSalesStatistics(creatorId, parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/product")
    public ResponseEntity<List<StatisticsWithProductDTO>> getProductStatistics(
            @RequestParam("creatorId") String creatorId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam(value = "category", required = false) Long category, // 카테고리 필터 추가
            @RequestParam(value = "searchTerm", required = false) String searchTerm // 검색어 필터 추가
    ) {
        // 날짜 변환
        LocalDateTime parsedStartDate = LocalDateTime.parse(startDate.trim());
        LocalDateTime parsedEndDate = LocalDateTime.parse(endDate.trim());

        // 서비스 호출
        List<StatisticsWithProductDTO> productStatistics =
                statisticsService.getProductSalesStatistics( creatorId, parsedStartDate, parsedEndDate, category, searchTerm != null ? searchTerm.trim() : null
                );

        // 응답 반환
        return ResponseEntity.ok(productStatistics);
    }
}
