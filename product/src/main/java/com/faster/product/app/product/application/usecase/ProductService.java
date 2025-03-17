package com.faster.product.app.product.application.usecase;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.faster.product.app.product.application.dto.request.UpdateProductApplicationRequestDto;
import com.faster.product.app.product.application.dto.response.GetProductDetailApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.UpdateProductApplicationResponseDto;
import com.faster.product.app.product.application.dto.request.SaveProductApplicationRequestDto;
import java.util.UUID;

public interface ProductService {

  GetProductDetailApplicationResponseDto getProductById(UUID productId);

  void deleteProductById(CurrentUserInfoDto userInfo, UUID productId);

  UpdateProductApplicationResponseDto updateProductById(
      CurrentUserInfoDto userInfo, UUID productId, UpdateProductApplicationRequestDto requestDto);

  UUID saveProduct(CurrentUserInfoDto userInfo, SaveProductApplicationRequestDto applicationRequestDto);
}
