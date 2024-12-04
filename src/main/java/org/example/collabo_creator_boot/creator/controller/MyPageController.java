package org.example.collabo_creator_boot.creator.controller;

import lombok.RequiredArgsConstructor;
import org.example.collabo_creator_boot.creator.dto.MyPageDTO;
import org.example.collabo_creator_boot.creator.service.MyPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping()
    public ResponseEntity<MyPageDTO> getMyPage(@RequestParam("creatorId") String creatorId) {
        MyPageDTO myPageDTO = myPageService.getMyPage(creatorId);
        return ResponseEntity.ok(myPageDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateMyPage(
            @RequestParam("creatorId") String creatorId,
            @RequestBody MyPageDTO myPageDTO) {
        myPageService.updateMyPage(creatorId, myPageDTO);
        return ResponseEntity.ok("마이페이지가 성공적으로 수정되었습니다.");
    }
}
