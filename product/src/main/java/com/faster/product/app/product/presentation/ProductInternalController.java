package com.faster.product.app.product.presentation;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.faster.product.app.product.application.usecase.ProductService;
import com.faster.product.app.product.presentation.dto.request.UpdateProductHubRequestDto;
import com.faster.product.app.product.presentation.dto.request.UpdateStocksRequestDto;
import com.faster.product.app.product.presentation.dto.response.UpdateProductHubResponseDto;
import com.faster.product.app.product.presentation.dto.response.UpdateStocksResponseDto;
import com.faster.product.app.product.presentation.dto.response.GetProductsResponseDto;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  @AuthCheck(roles={UserRole.ROLE_MASTER, UserRole.ROLE_COMPANY})
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

  @AuthCheck(roles={UserRole.ROLE_MASTER, UserRole.ROLE_COMPANY})
  @PatchMapping("/stocks")
  public ResponseEntity<ApiResponse<UpdateStocksResponseDto>> updateProductStocks(
      @RequestBody @Valid UpdateStocksRequestDto requestDto
  ) {

    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "상품 재고가 성공적으로 수정되었습니다.",
            HttpStatus.OK.value(),
            UpdateStocksResponseDto.from(
                productService.updateProductStocksInternal(requestDto.toSortedApplicationRequestDto()))
        ));
  }

  @AuthCheck(roles={UserRole.ROLE_MASTER, UserRole.ROLE_COMPANY})
  @PatchMapping("/hub")
  public ResponseEntity<ApiResponse<UpdateProductHubResponseDto>> updateProductHubByCompanyId(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @RequestBody @Valid UpdateProductHubRequestDto requestDto
  ) {

    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "상품 허브가 성공적으로 수정되었습니다.",
            HttpStatus.OK.value(),
            UpdateProductHubResponseDto.from(
                productService.updateProductHubByCompanyIdInternal(userInfo, requestDto.toApplicationDto()))
        ));
  }

  @AuthCheck(roles={UserRole.ROLE_MASTER, UserRole.ROLE_COMPANY})
  @DeleteMapping
  public ResponseEntity<ApiResponse<Void>> deleteProductByCompanyId(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @RequestParam UUID companyId
  ) {

    productService.deleteProductByCompanyIdInternal(userInfo, companyId);
    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "해당 업체의 상품 목록이 성공적으로 삭제되었습니다.",
            HttpStatus.OK.value(),
            null
        ));
  }
}
