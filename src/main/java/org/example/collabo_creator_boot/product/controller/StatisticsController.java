package org.example.collabo_creator_boot.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.product.dto.ProductListDTO;
import org.example.collabo_creator_boot.product.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@Log4j2
public class StatisticsController {

    private final ProductService productService;

    @GetMapping("")
    public PageResponseDTO<ProductListDTO> productList(PageRequestDTO pageRequestDTO){
        return productService.getProductList(pageRequestDTO);
    }
}
