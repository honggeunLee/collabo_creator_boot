package org.example.collabo_creator_boot.creator.controller;

import lombok.RequiredArgsConstructor;
import org.example.collabo_creator_boot.creator.dto.OfflineStoreListDTO;
import org.example.collabo_creator_boot.creator.dto.OfflineStoreRegisterDTO;
import org.example.collabo_creator_boot.creator.service.CreatorOfflineStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offlinestore")
@RequiredArgsConstructor
public class CreatorOfflineStoreController {

    private final CreatorOfflineStoreService creatorOfflineStoreService;

    @GetMapping("/list")
    public ResponseEntity<List<OfflineStoreListDTO>> getOfflineStores(@RequestParam("creatorId") String creatorId) {
        List<OfflineStoreListDTO> stores = creatorOfflineStoreService.getOfflineStoresByCreator(creatorId);
        return ResponseEntity.ok(stores);
    }

    @PostMapping("/add")
    public ResponseEntity<Long> registerOfflineStore(
            @RequestParam("creatorId") String creatorId,
            @RequestBody OfflineStoreRegisterDTO dto) {
        Long storeNo = creatorOfflineStoreService.registerOfflineStore(creatorId, dto);
        return ResponseEntity.ok(storeNo);
    }

    @PutMapping("/update/{storeNo}")
    public ResponseEntity<Long> updateOfflineStore(
            @PathVariable("storeNo") Long storeNo,
            @RequestBody OfflineStoreRegisterDTO offlineStoreRegisterDTO) {
        Long updatedStoreNo = creatorOfflineStoreService.updateOfflineStore(storeNo, offlineStoreRegisterDTO);
        return ResponseEntity.ok(updatedStoreNo);
    }

    @DeleteMapping("/delete/{storeNo}")
    public ResponseEntity<Void> deleteOfflineStore(@PathVariable("storeNo") Long storeNo) {
        creatorOfflineStoreService.deleteOfflineStore(storeNo);
        return ResponseEntity.noContent().build();
    }



}
