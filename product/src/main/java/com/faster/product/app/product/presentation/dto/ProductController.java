package com.faster.product.app.product.presentation.dto;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.faster.product.app.product.application.usecase.ProductService;
import com.faster.product.app.product.presentation.dto.response.GetProductDetailResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/products")
@RequiredArgsConstructor
@RestController
public class ProductController {
  private final ProductService productService;

  @GetMapping("/{productId}")
  public ResponseEntity<ApiResponse<GetProductDetailResponseDto>> getProductById(
      @PathVariable UUID productId) {

    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "상품이 성공적으로 조회되었습니다.",
            HttpStatus.OK.value(),
            GetProductDetailResponseDto.from(productService.getProductById(productId))));
  }

}
