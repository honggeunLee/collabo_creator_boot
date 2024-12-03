package org.example.collabo_creator_boot.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnAImageDTO {
    private Integer qnaImageNo;
    private String qnaImageUrl;
    private Integer qnaImageOrd;
}
