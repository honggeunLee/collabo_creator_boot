package org.example.collabo_creator_boot.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.product.dto.ProductListDTO;
import org.example.collabo_creator_boot.product.dto.ProductReadDTO;
import org.example.collabo_creator_boot.product.dto.ProductRegisterDTO;
import org.example.collabo_creator_boot.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public PageResponseDTO<ProductListDTO>productList(PageRequestDTO pageRequestDTO){
        return productService.getProductList(pageRequestDTO);
    }

    @GetMapping("/read/{productNo}")
    public ResponseEntity<ProductReadDTO>productDetails(@PathVariable(name = "productNo")Long productNo){
        return ResponseEntity.ok(productService.readProductDetails(productNo));
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addProduct(@RequestBody ProductRegisterDTO productRegisterDTO) {
        Long productId = productService.registerProduct(productRegisterDTO);
        return ResponseEntity.ok(productId);
    }
}
