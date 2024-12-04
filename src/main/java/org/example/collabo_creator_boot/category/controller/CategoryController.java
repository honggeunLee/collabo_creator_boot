package org.example.collabo_creator_boot.category.controller;

import lombok.RequiredArgsConstructor;
import org.example.collabo_creator_boot.category.dto.UserCategoryDTO;
import org.example.collabo_creator_boot.category.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("api/product/usercategory")
    public List<UserCategoryDTO> getCategoriesByCreator(@RequestParam("creatorId") String creatorId) {
        return categoryService.getCategoriesByCreator(creatorId);
    }

    @PostMapping("api/product/addusercategory")
    public ResponseEntity<UserCategoryDTO> addCategory(@RequestParam("creatorId") String creatorId,
                                                       @RequestBody UserCategoryDTO categoryDTO) {
        try {
            // 서비스에서 카테고리 추가 처리
            UserCategoryDTO createdCategory = categoryService.addCategory(creatorId, categoryDTO);
            return ResponseEntity.ok(createdCategory); // 성공 응답
        } catch (IllegalArgumentException e) {
            // 요청 데이터 문제 처리
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(500).body(null);
        }
    }

}
