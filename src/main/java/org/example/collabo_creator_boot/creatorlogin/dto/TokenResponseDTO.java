package org.example.collabo_creator_boot.creatorlogin.dto;

import lombok.Data;

@Data
public class TokenResponseDTO {

    private String creatorId;
    private String accessToken;
    private String refreshToken;
    private int status;
    private String message;
}
