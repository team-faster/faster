package com.faster.product.app.product.application.usecase;

import com.common.exception.CustomException;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.faster.product.app.global.exception.ProductErrorCode;
import com.faster.product.app.product.application.dto.GetProductDetailApplicationResponseDto;
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

  @Override
  public void deleteProductById(CurrentUserInfoDto userInfo, UUID productId) {

    Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
        .orElseThrow(() -> new CustomException(ProductErrorCode.INVALID_ID));

    if (UserRole.ROLE_COMPANY == userInfo.role()) {
      this.checkIfValidAccessToDelete(userInfo.userId(), product.getCompanyId());
    }
    LocalDateTime localDateTime = LocalDateTime.now();
    product.delete(localDateTime, userInfo.userId());
  }

  private void checkIfValidAccessToDelete(Long userId, UUID companyId) {
//    // 업체 정보 조회해오기
//    // companyResponse
//    if (!companyResponse.getId().equals(companyId)) {
//      throw new CustomException(ProductErrorCode.FORBIDDEN_ACCESS);
//    }
  }
}
