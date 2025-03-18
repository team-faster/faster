package com.faster.product.app.product.presentation;

import com.common.response.ApiResponse;
import com.faster.product.app.product.application.usecase.ProductService;
import com.faster.product.app.product.presentation.dto.request.UpdateStocksRequestDto;
import com.faster.product.app.product.presentation.dto.response.UpdateStocksResponseDto;
import com.faster.product.app.product.presentation.dto.response.GetProductsResponseDto;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/internal/products")
@RequiredArgsConstructor
@RestController
public class ProductInternalController {
  private final ProductService productService;

  @GetMapping
  public ResponseEntity<ApiResponse<GetProductsResponseDto>> getProductList(
      @RequestParam Set<UUID> ids
  ) {

    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "상품 목록이 성공적으로 조회되었습니다.",
            HttpStatus.OK.value(),
            GetProductsResponseDto.from(productService.getProductList(ids))
        ));
  }

  @PatchMapping
  public ResponseEntity<ApiResponse<UpdateStocksResponseDto>> updateProductStocks(
      @RequestBody UpdateStocksRequestDto requestDto
  ) {

    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "상품 재고가 성공적으로 수정되었습니다.",
            HttpStatus.OK.value(),
            UpdateStocksResponseDto.from(
                productService.updateProductStocks(requestDto.toApplicationRequestDto()))
        ));
  }
}
