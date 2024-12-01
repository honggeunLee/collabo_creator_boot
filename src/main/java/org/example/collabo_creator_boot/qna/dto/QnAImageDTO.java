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
    private int qnaImageNo;
    private String qnaImageUrl;
    private int qnaImageOrd;
}
