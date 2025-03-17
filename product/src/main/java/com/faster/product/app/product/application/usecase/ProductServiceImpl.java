package com.faster.product.app.product.application.usecase;

import com.faster.product.app.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
}
