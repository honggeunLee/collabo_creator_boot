package org.example.collabo_creator_boot.creator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfflineStoreRegisterDTO {
    private Long storeNo;
    private String storeImage;
    private String storeName;
    private String storeAddress;
    private String latitude;
    private String longitude;
}
