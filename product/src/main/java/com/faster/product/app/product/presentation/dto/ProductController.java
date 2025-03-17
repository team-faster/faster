package com.faster.product.app.product.presentation.dto;

import com.faster.product.app.product.application.usecase.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductController {
  private final ProductService productService;

}
