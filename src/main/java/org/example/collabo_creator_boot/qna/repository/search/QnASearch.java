package org.example.collabo_creator_boot.qna.repository.search;

import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.qna.dto.QnAListDTO;

public interface QnASearch {

    PageResponseDTO<QnAListDTO> qnAListByCreator(String creatorId, PageRequestDTO pageRequestDTO);
}
