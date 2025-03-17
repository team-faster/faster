package com.faster.product.app.product.application.usecase;

import com.faster.product.app.product.application.dto.GetProductDetailApplicationResponseDto;
import java.util.UUID;

public interface ProductService {

  GetProductDetailApplicationResponseDto getProductById(UUID productId);
}
