package org.example.collabo_creator_boot.order.controller;

import org.example.collabo_creator_boot.order.dto.RefundNCancelDTO;
import org.example.collabo_creator_boot.order.service.RefundNCancelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/refundncancel")
public class RefundNCancelController {

    private final RefundNCancelService refundNCancelService;

    public RefundNCancelController(RefundNCancelService refundNCancelService) {
        this.refundNCancelService = refundNCancelService;
    }

    @GetMapping("")
    public ResponseEntity<List<RefundNCancelDTO>> getRefundAndCancelStatistics(
            @RequestParam("creatorId") String creatorId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        LocalDateTime parsedStartDate = LocalDateTime.parse(startDate.trim());
        LocalDateTime parsedEndDate = LocalDateTime.parse(endDate.trim());

        List<RefundNCancelDTO> statistics = refundNCancelService.getRefundAndCancelStatistics(creatorId, parsedStartDate, parsedEndDate);

        return ResponseEntity.ok(statistics);
    }
}
