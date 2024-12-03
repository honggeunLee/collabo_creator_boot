package org.example.collabo_creator_boot.review.repository.search;

import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.review.dto.ReviewListDTO;

public interface ReviewSearch {

    PageResponseDTO<ReviewListDTO> reviewListByCreator(String creatorId, PageRequestDTO pageRequestDTO);
}
