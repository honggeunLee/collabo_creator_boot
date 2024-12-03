package org.example.collabo_creator_boot.creator.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.example.collabo_creator_boot.creator.domain.CreatorEntity;
import org.example.collabo_creator_boot.creator.domain.QCreatorOfflineStoreEntity;
import org.example.collabo_creator_boot.creator.dto.OfflineStoreListDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CreatorSearchImpl extends QuerydslRepositorySupport implements CreatorSearch {

    public CreatorSearchImpl() {super(CreatorEntity.class);}

    @Override
    public List<OfflineStoreListDTO> offlineStoreListByCreator(String creatorId) {
        QCreatorOfflineStoreEntity store = QCreatorOfflineStoreEntity.creatorOfflineStoreEntity;

        return new JPAQueryFactory(getEntityManager())
                .select(Projections.constructor(OfflineStoreListDTO.class,
                        store.storeNo,
                        store.storeName,
                        store.storeAddress,
                        store.storeImage,
                        store.latitude.stringValue(),
                        store.longitude.stringValue()
                ))
                .from(store)
                .where(store.creatorEntity.creatorId.eq(creatorId))
                .fetch();
    }
}
