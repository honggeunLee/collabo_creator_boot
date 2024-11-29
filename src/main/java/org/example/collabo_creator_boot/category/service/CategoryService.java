package org.example.collabo_creator_boot.category.service;

import lombok.RequiredArgsConstructor;
import org.example.collabo_creator_boot.category.domain.CategoryEntity;
import org.example.collabo_creator_boot.category.dto.UserCategoryDTO;
import org.example.collabo_creator_boot.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<UserCategoryDTO> getCategoriesByCreator(String creatorId) {

        List<CategoryEntity> categories = categoryRepository.findCategoriesByCreatorId(creatorId);

        return categories.stream()
                .map(category -> new UserCategoryDTO(
                        category.getCategoryNo(),
                        category.getCategoryName()
                ))
                .collect(Collectors.toList());
    }
}
