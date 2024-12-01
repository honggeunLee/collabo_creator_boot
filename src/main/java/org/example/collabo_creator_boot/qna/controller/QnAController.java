package org.example.collabo_creator_boot.qna.controller;

import lombok.RequiredArgsConstructor;
import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.qna.dto.QnAListDTO;
import org.example.collabo_creator_boot.qna.service.QnAService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class QnAController {

    private final QnAService qnaService;

    @GetMapping("/list")
    public ResponseEntity<PageResponseDTO<QnAListDTO>> getQnAList(
            @CookieValue(value = "creatorId", required = false) String creatorId,
            @ModelAttribute PageRequestDTO pageRequestDTO) {
        if (creatorId == null || creatorId.isEmpty()) {
            throw new IllegalArgumentException("Creator ID is missing.");
        }

        PageResponseDTO<QnAListDTO> response = qnaService.getQnAListByCreator(creatorId, pageRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{qnaNo}/answer")
    public ResponseEntity<Void> addAnswer(@PathVariable Long qnaNo, @RequestBody Map<String, String> request) {
        String answer = request.get("answer");
        qnaService.addAnswer(qnaNo, answer);
        return ResponseEntity.ok().build();
    }
}
