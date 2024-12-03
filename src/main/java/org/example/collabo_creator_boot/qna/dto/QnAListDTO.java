package org.example.collabo_creator_boot.qna.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnAListDTO {
    private Long qnaNo;
    private String productName;
    private String customerName;
    private String question;
    private String answer;
    private List<QnAImageDTO> qnaImages;
    private LocalDateTime createdAt;

    @JsonProperty("createdAt")
    public String getFormattedCreatedAt() {
        if (createdAt != null) {
            return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return null;
    }
}
