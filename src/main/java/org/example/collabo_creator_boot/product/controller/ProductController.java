package org.example.collabo_creator_boot.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.collabo_creator_boot.common.PageRequestDTO;
import org.example.collabo_creator_boot.common.PageResponseDTO;
import org.example.collabo_creator_boot.creator.dto.MyPageDTO;
import org.example.collabo_creator_boot.product.dto.ProductImageDTO;
import org.example.collabo_creator_boot.product.dto.ProductListDTO;
import org.example.collabo_creator_boot.product.dto.ProductReadDTO;
import org.example.collabo_creator_boot.product.dto.ProductRegisterDTO;
import org.example.collabo_creator_boot.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<PageResponseDTO<ProductListDTO>> getCreatorProductList(
            @RequestParam("creatorId") String creatorId,
            @RequestParam(value = "searchQuery", required = false) String searchQuery,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "categoryNo", required = false) Long categoryNo,
            @ModelAttribute PageRequestDTO pageRequestDTO) {

        PageResponseDTO<ProductListDTO> response = productService.getCreatorProductList(
                creatorId, pageRequestDTO, searchQuery, status, categoryNo);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/read/{productNo}")
    public ResponseEntity<ProductReadDTO>productDetails(@PathVariable(name = "productNo")Long productNo){
        return ResponseEntity.ok(productService.readProductDetails(productNo));
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addProduct(@RequestBody ProductRegisterDTO productRegisterDTO) {
        log.info("Adding new product: {}", productRegisterDTO);
        Long productId = productService.registerProduct(productRegisterDTO);
        return ResponseEntity.ok(productId);
    }

    @PostMapping("/img")
    public ResponseEntity<List<ProductImageDTO>> uploadProductImages(
            @RequestParam("productNo") Long productNo,
            @RequestBody List<ProductImageDTO> imageDTOs) {

        try {
            List<ProductImageDTO> savedImages = productService.saveImages(productNo, imageDTOs);
            return ResponseEntity.ok(savedImages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @PutMapping("/modify/{productNo}")
    public ResponseEntity<String> updateProduct(
            @RequestParam("creatorId") String creatorId,
            @PathVariable("productNo") Long productNo,
            @Valid @RequestBody ProductReadDTO productReadDTO) {
        try {
            // 서비스 호출하여 업데이트 수행
            productService.updateProduct(creatorId, productNo, productReadDTO);
            return ResponseEntity.ok("상품 정보가 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("상품 정보 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @DeleteMapping("/img/{imageId}")
    public ResponseEntity<String> deleteProductImage(@PathVariable("imageId") Long imageId) {
        try {
            productService.deleteProductImage(imageId);
            return ResponseEntity.ok("이미지가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            log.error("이미지 삭제 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("이미지 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


}
