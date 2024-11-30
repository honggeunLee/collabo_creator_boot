package org.example.collabo_creator_boot.order.repository.search;

import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.order.dto.OrderListDTO;

public interface OrderSearch {

    PageResponseDTO<OrderListDTO> orderList(PageRequestDTO pageRequestDTO);
}
