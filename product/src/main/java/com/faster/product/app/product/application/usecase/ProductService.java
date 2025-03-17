package com.faster.product.app.product.application.usecase;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.faster.product.app.product.application.dto.GetProductDetailApplicationResponseDto;
import java.util.UUID;

public interface ProductService {

  GetProductDetailApplicationResponseDto getProductById(UUID productId);

  void deleteProductById(CurrentUserInfoDto userInfo, UUID productId);
}
