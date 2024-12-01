package org.example.collabo_creator_boot.creatorlogin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreatorLoginDTO {
    private String creatorID;
    private String creatorPassword;
    private String creatorName;

    public CreatorLoginDTO(String creatorID, String creatorPassword, String creatorName) {
        this.creatorID = creatorID;
        this.creatorPassword = creatorPassword;
        this.creatorName = creatorName;
    }
}
