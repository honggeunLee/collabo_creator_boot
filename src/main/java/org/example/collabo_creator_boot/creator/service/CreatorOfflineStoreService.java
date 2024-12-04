package org.example.collabo_creator_boot.creator.service;

import lombok.RequiredArgsConstructor;
import org.example.collabo_creator_boot.creator.domain.CreatorEntity;
import org.example.collabo_creator_boot.creator.domain.CreatorOfflineStoreEntity;
import org.example.collabo_creator_boot.creator.dto.OfflineStoreListDTO;
import org.example.collabo_creator_boot.creator.dto.OfflineStoreRegisterDTO;
import org.example.collabo_creator_boot.creator.repository.CreatorOfflineStoreRepository;
import org.example.collabo_creator_boot.creator.repository.CreatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatorOfflineStoreService {
    private static final Logger log = LoggerFactory.getLogger(CreatorOfflineStoreService.class);
    private final CreatorRepository creatorRepository;
    private final CreatorOfflineStoreRepository creatorOfflineStoreRepository;
    private final GeocodingService geocodingService;

    public List<OfflineStoreListDTO> getOfflineStoresByCreator(String creatorId) {
        return creatorRepository.offlineStoreListByCreator(creatorId);
    }

    public Long registerOfflineStore(String creatorId, OfflineStoreRegisterDTO dto) {
        // 주소로 위도와 경도 변환
        double[] coordinates = geocodingService.convertAddressToCoordinates(dto.getStoreAddress());
        BigDecimal latitude = BigDecimal.valueOf(coordinates[0]);
        BigDecimal longitude = BigDecimal.valueOf(coordinates[1]);

        CreatorEntity creatorEntity = creatorRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid creatorId"));

        CreatorOfflineStoreEntity offlineStoreEntity = CreatorOfflineStoreEntity.builder()
                .storeName(dto.getStoreName())
                .storeAddress(dto.getStoreAddress())
                .latitude(latitude)
                .longitude(longitude)
                .storeImage(dto.getStoreImage()) // 클라이언트에서 전달받은 URL
                .creatorEntity(creatorEntity)
                .build();

        creatorOfflineStoreRepository.save(offlineStoreEntity);
        return offlineStoreEntity.getStoreNo();
    }

    public Long updateOfflineStore(Long storeNo, OfflineStoreRegisterDTO dto) {
        // 기존 Store 조회
        CreatorOfflineStoreEntity existingStore = creatorOfflineStoreRepository.findById(storeNo)
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));

        // 주소를 위도와 경도로 변환
        double[] coordinates = geocodingService.convertAddressToCoordinates(dto.getStoreAddress());
        BigDecimal latitude = BigDecimal.valueOf(coordinates[0]);
        BigDecimal longitude = BigDecimal.valueOf(coordinates[1]);

        // 빌더 패턴을 사용하여 기존 데이터에서 업데이트
        CreatorOfflineStoreEntity updatedStore = CreatorOfflineStoreEntity.builder()
                .storeNo(existingStore.getStoreNo())
                .storeName(dto.getStoreName())
                .storeAddress(dto.getStoreAddress())
                .storeImage(dto.getStoreImage())
                .latitude(latitude)
                .longitude(longitude)
                .creatorEntity(existingStore.getCreatorEntity()) // 기존 Creator 유지
                .build();

        // 새 엔티티 저장
        creatorOfflineStoreRepository.save(updatedStore);
        return updatedStore.getStoreNo();
    }

    public void deleteOfflineStore(Long storeNo) {
        creatorOfflineStoreRepository.deleteById(storeNo);
    }

}
