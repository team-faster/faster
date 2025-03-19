package com.faster.product.app.product.presentation;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.faster.product.app.product.application.usecase.ProductService;
import com.faster.product.app.product.presentation.dto.request.SaveProductRequestDto;
import com.faster.product.app.product.presentation.dto.request.UpdateProductRequestDto;
import com.faster.product.app.product.presentation.dto.response.GetProductDetailResponseDto;
import com.faster.product.app.product.presentation.dto.response.UpdateProductResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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

  @AuthCheck(roles = {UserRole.ROLE_MASTER, UserRole.ROLE_COMPANY})
  @PostMapping
  public ResponseEntity<ApiResponse<Void>> saveProduct(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @RequestBody SaveProductRequestDto requestDto) {

    UUID productId = productService.saveProduct(userInfo, requestDto.toApplicationRequestDto());
    return ResponseEntity.created(
            UriComponentsBuilder.fromUriString("/api/products/{productId}")
                .buildAndExpand(productId)
                .toUri()
        )
        .body(new ApiResponse<>(
            "상품이 성공적으로 등록되었습니다.",
            HttpStatus.OK.value(),
            null
        ));
  }


  @AuthCheck(roles = {UserRole.ROLE_MASTER, UserRole.ROLE_COMPANY})
  @PatchMapping("/{productId}")
  public ResponseEntity<ApiResponse<UpdateProductResponseDto>> updateProductById(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PathVariable UUID productId, @RequestBody UpdateProductRequestDto requestDto) {

    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "상품이 성공적으로 수정되었습니다.",
            HttpStatus.OK.value(),
            UpdateProductResponseDto.from(
                productService.updateProductById(userInfo, productId, requestDto.toApplicationRequestDto())
            )));
  }

  @AuthCheck(roles = {UserRole.ROLE_MASTER, UserRole.ROLE_COMPANY})
  @DeleteMapping("/{productId}")
  public ResponseEntity<ApiResponse<Void>> deleteProductById(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PathVariable UUID productId) {

    productService.deleteProductById(userInfo, productId);
    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "상품이 성공적으로 삭제되었습니다.",
            HttpStatus.OK.value(),
            null));
  }
}
