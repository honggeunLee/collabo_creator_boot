package org.example.collabo_creator_boot.creatorlogin.dto;

import lombok.Data;

@Data
public class ErrorResponseDTO {

    private int status;
    private String message;
}