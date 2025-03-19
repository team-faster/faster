package com.faster.product.app.product.presentation;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.common.response.PageResponse;
import com.faster.product.app.product.application.dto.response.SearchProductApplicationResponseDto;
import com.faster.product.app.product.application.dto.request.SearchProductConditionDto;
import com.faster.product.app.product.application.usecase.ProductService;
import com.faster.product.app.product.presentation.dto.request.SaveProductRequestDto;
import com.faster.product.app.product.presentation.dto.request.UpdateProductRequestDto;
import com.faster.product.app.product.presentation.dto.response.GetProductDetailResponseDto;
import com.faster.product.app.product.presentation.dto.response.SearchProductResponseDto;
import com.faster.product.app.product.presentation.dto.response.UpdateProductResponseDto;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/api/products")
@RequiredArgsConstructor
@RestController
public class ProductController {
  private final ProductService productService;

  @GetMapping
  public ResponseEntity<ApiResponse<PageResponse<SearchProductResponseDto>>> getProducts(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @PageableDefault
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
          @SortDefault(sort = "modifiedAt", direction = Sort.Direction.DESC)
      }) Pageable pageable,
      @RequestParam(name = "company-id", required = false) UUID companyId,
      @RequestParam(name = "company-name", required = false) String companyName,
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "min-price", required = false)
      @Positive @Digits(integer = 15, fraction = 2) BigDecimal minPrice,
      @RequestParam(name = "max-price", required = false)
      @Positive @Digits(integer = 15, fraction = 2) BigDecimal maxPrice,
      @RequestParam(name = "is-deleted", required = false) Boolean isDeleted,
      @RequestParam(name = "start-created-at", required = false) LocalDateTime startCreatedAt,
      @RequestParam(name = "end-created-at", required = false) LocalDateTime endCreatedAt
  ) {

    PageResponse<SearchProductApplicationResponseDto> pageResponse =
        productService.getProductsByCondition(userInfo, pageable, SearchProductConditionDto.of(
            companyId, companyName, name, minPrice, maxPrice, isDeleted, startCreatedAt, endCreatedAt));

    return ResponseEntity.ok()
        .body(ApiResponse.of(
            HttpStatus.OK,
            "상품이 성공적으로 조회되었습니다.",
            pageResponse.map(
                SearchProductResponseDto::from)));
  }

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
