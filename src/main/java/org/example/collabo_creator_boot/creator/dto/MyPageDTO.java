package org.example.collabo_creator_boot.creator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageDTO {
    private String creatorName;
    private String creatorEmail;
    private String creatorPhone;

    private String creatorBank;
    private String creatorAccount;

    private String backgroundImg;
    private String logoImg;

    private Boolean emailNotifications;
    private Boolean smsNotifications;

}
