package org.example.collabo_creator_boot.creatorlogin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TokenRequestDTO {

    @NotNull
    private String creatorId;

    @NotNull
    private String creatorPassword;
}
