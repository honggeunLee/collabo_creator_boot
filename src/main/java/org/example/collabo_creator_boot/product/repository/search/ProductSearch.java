package org.example.collabo_creator_boot.product.repository.search;

import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.product.dto.ProductListDTO;

public interface ProductSearch {

    PageResponseDTO<ProductListDTO>productList(PageRequestDTO pageRequestDTO);

}
