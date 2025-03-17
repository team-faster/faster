package com.faster.product.app.product.application.usecase;

import com.common.exception.CustomException;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.faster.product.app.global.exception.ProductErrorCode;
import com.faster.product.app.product.application.dto.request.UpdateProductApplicationRequestDto;
import com.faster.product.app.product.application.dto.response.GetProductDetailApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.UpdateProductApplicationResponseDto;
import com.faster.product.app.product.domain.entity.Product;
import com.faster.product.app.product.domain.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;

  @Override
  public GetProductDetailApplicationResponseDto getProductById(UUID productId) {

    Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
        .orElseThrow(() -> new CustomException(ProductErrorCode.INVALID_ID));
    return GetProductDetailApplicationResponseDto.from(product);
  }

  @Transactional
  @Override
  public UpdateProductApplicationResponseDto updateProductById(CurrentUserInfoDto userInfo,
      UUID productId, UpdateProductApplicationRequestDto requestDto) {

    Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
        .orElseThrow(() -> new CustomException(ProductErrorCode.INVALID_ID));

    if (UserRole.ROLE_COMPANY == userInfo.role()) {
      this.checkIfValidAccess(userInfo.userId(), product.getCompanyId());
    }
    product.updateContent(requestDto.name(), requestDto.price(), requestDto.quantity(), requestDto.description());
    return UpdateProductApplicationResponseDto.from(product);
  }

  @Override
  public void deleteProductById(CurrentUserInfoDto userInfo, UUID productId) {

    Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
        .orElseThrow(() -> new CustomException(ProductErrorCode.INVALID_ID));

    if (UserRole.ROLE_COMPANY == userInfo.role()) {
      this.checkIfValidAccess(userInfo.userId(), product.getCompanyId());
    }
    LocalDateTime localDateTime = LocalDateTime.now();
    product.delete(localDateTime, userInfo.userId());
  }

  private void checkIfValidAccess(Long userId, UUID companyId) {
//    // 업체 정보 조회해오기
//    // companyResponse
//    if (!companyResponse.getId().equals(companyId)) {
//      throw new CustomException(ProductErrorCode.FORBIDDEN_ACCESS);
//    }
  }
}
