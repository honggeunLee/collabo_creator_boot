package org.example.collabo_creator_boot.category.service;

import lombok.RequiredArgsConstructor;
import org.example.collabo_creator_boot.category.domain.CategoryEntity;
import org.example.collabo_creator_boot.category.dto.UserCategoryDTO;
import org.example.collabo_creator_boot.category.repository.CategoryRepository;
import org.example.collabo_creator_boot.creator.domain.CreatorEntity;
import org.example.collabo_creator_boot.creator.repository.CreatorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CreatorRepository creatorRepository;


    public List<UserCategoryDTO> getCategoriesByCreator(String creatorId) {

        List<CategoryEntity> categories = categoryRepository.findCategoriesByCreatorId(creatorId);

        return categories.stream()
                .map(category -> new UserCategoryDTO(
                        category.getCategoryNo(),
                        category.getCategoryName()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserCategoryDTO addCategory(String creatorId, UserCategoryDTO categoryDTO) {
        // Creator 조회
        CreatorEntity creator = creatorRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException("Creator not found with ID: " + creatorId));

        // CategoryEntity 생성
        CategoryEntity category = CategoryEntity.builder()
                .categoryName(categoryDTO.getCategoryName())
                .creatorEntity(creator)
                .build();

        // 데이터 저장
        CategoryEntity savedCategory = categoryRepository.save(category);

        // DTO 반환
        return new UserCategoryDTO(savedCategory.getCategoryNo(), savedCategory.getCategoryName());
    }
}
