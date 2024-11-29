package org.example.collabo_creator_boot.category.repository.search;

import org.example.collabo_creator_boot.category.domain.CategoryEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CategorySearchImpl extends QuerydslRepositorySupport implements CategorySearch {

    public CategorySearchImpl() {super(CategoryEntity.class);}

}
