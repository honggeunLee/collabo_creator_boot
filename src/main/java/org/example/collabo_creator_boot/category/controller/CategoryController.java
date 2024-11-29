package org.example.collabo_creator_boot.category.controller;

import lombok.RequiredArgsConstructor;
import org.example.collabo_creator_boot.category.dto.UserCategoryDTO;
import org.example.collabo_creator_boot.category.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("api/product/usercategory")
    public List<UserCategoryDTO> getCategoriesByCreator(@RequestParam("creatorId") String creatorId) {
        return categoryService.getCategoriesByCreator(creatorId);
    }
}
