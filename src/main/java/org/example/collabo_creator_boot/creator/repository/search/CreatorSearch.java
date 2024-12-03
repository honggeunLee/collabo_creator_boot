package org.example.collabo_creator_boot.creator.repository.search;

import org.example.collabo_creator_boot.creator.dto.OfflineStoreListDTO;

import java.util.List;

public interface CreatorSearch {

    List<OfflineStoreListDTO> offlineStoreListByCreator(String creatorId);
}
