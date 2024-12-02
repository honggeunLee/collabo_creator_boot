package org.example.collabo_creator_boot.creator.service;

import lombok.RequiredArgsConstructor;
import org.example.collabo_creator_boot.creator.domain.CreatorEntity;
import org.example.collabo_creator_boot.creator.domain.CreatorOfflineStoreEntity;
import org.example.collabo_creator_boot.creator.dto.OfflineStoreListDTO;
import org.example.collabo_creator_boot.creator.dto.OfflineStoreRegisterDTO;
import org.example.collabo_creator_boot.creator.repository.CreatorOfflineStoreRepository;
import org.example.collabo_creator_boot.creator.repository.CreatorRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatorOfflineStoreService {
    private final CreatorRepository creatorRepository;
    private final CreatorOfflineStoreRepository creatorOfflineStoreRepository;

    public List<OfflineStoreListDTO> getOfflineStoresByCreator(String creatorId) {
        return creatorRepository.offlineStoreListByCreator(creatorId);
    }

    public Long registerOfflineStore(String creatorId, OfflineStoreRegisterDTO dto) {
        // creatorId로 CreatorEntity 조회
        CreatorEntity creatorEntity = creatorRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid creatorId"));

        // CreatorOfflineStoreEntity 생성
        CreatorOfflineStoreEntity offlineStoreEntity = CreatorOfflineStoreEntity.builder()
                .storeNo(dto.getStoreNo())
                .storeName(dto.getStoreName())
                .storeAddress(dto.getStoreAddress())
                .storeImage(dto.getStoreImage())
                .latitude(new BigDecimal(dto.getLatitude()))
                .longitude(new BigDecimal(dto.getLongitude()))
                .creatorEntity(creatorEntity)
                .build();

        // 엔티티 저장
        creatorOfflineStoreRepository.save(offlineStoreEntity);

        return offlineStoreEntity.getStoreNo(); // 저장된 StoreNo 반환
    }

    public Long updateOfflineStore(Long storeNo, OfflineStoreRegisterDTO dto) {
        CreatorOfflineStoreEntity existingStore = creatorOfflineStoreRepository.findById(storeNo)
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));

        existingStore = CreatorOfflineStoreEntity.builder()
                .storeNo(storeNo)
                .storeName(dto.getStoreName())
                .storeAddress(dto.getStoreAddress())
                .latitude(new BigDecimal(dto.getLatitude()))
                .longitude(new BigDecimal(dto.getLongitude()))
                .storeImage(dto.getStoreImage())
                .creatorEntity(existingStore.getCreatorEntity()) // 기존 Creator 유지
                .build();

        creatorOfflineStoreRepository.save(existingStore);
        return existingStore.getStoreNo();
    }

    public void deleteOfflineStore(Long storeNo) {
        creatorOfflineStoreRepository.deleteById(storeNo);
    }
}
