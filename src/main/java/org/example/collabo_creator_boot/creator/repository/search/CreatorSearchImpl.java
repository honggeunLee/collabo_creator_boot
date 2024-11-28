package org.example.collabo_creator_boot.creator.repository.search;

import org.example.collabo_creator_boot.creator.domain.CreatorEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CreatorSearchImpl extends QuerydslRepositorySupport implements CreatorSearch {

    public CreatorSearchImpl() {super(CreatorEntity.class);}
}
