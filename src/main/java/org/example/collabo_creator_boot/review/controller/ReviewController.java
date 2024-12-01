package org.example.collabo_creator_boot.review.controller;

import lombok.RequiredArgsConstructor;
import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.review.dto.ReviewListDTO;
import org.example.collabo_creator_boot.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/list")
    public ResponseEntity<PageResponseDTO<ReviewListDTO>> getReviewList(
            @CookieValue(value = "creatorId", required = false) String creatorId,
            @ModelAttribute PageRequestDTO pageRequestDTO) {
        if (creatorId == null || creatorId.isEmpty()) {
            throw new IllegalArgumentException("Creator ID is missing.");
        }

        PageResponseDTO<ReviewListDTO> response = reviewService.getReviewList(creatorId, pageRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{reviewNo}/reply")
    public ResponseEntity<Void> addReply(@PathVariable Long reviewNo, @RequestBody Map<String, String> request) {
        String reply = request.get("reply");
        reviewService.addReply(reviewNo, reply);
        return ResponseEntity.ok().build();
    }
}
