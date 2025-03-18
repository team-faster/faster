package com.faster.product.app.product.application.usecase;

import com.common.exception.CustomException;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.faster.product.app.global.exception.ProductErrorCode;
import com.faster.product.app.product.application.dto.request.UpdateStocksApplicationRequestDto;
import com.faster.product.app.product.application.dto.request.UpdateStocksApplicationRequestDto.UpdateStockApplicationRequestDto;
import com.faster.product.app.product.application.dto.request.UpdateProductApplicationRequestDto;
import com.faster.product.app.product.application.dto.response.UpdateStocksApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.UpdateStocksApplicationResponseDto.UpdateStockApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.GetProductDetailApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.UpdateProductApplicationResponseDto;
import com.faster.product.app.product.application.dto.request.SaveProductApplicationRequestDto;
import com.faster.product.app.product.domain.entity.Product;
import com.faster.product.app.product.domain.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
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
  public UUID saveProduct(CurrentUserInfoDto userInfo,
      SaveProductApplicationRequestDto applicationRequestDto) {

    // todo. 전략패턴 리팩토링?
    // 마스터 사용자 - 업체 아이디로 업체 정보 조회해오기
    if (UserRole.ROLE_MASTER == userInfo.role()) {
      // applicationRequestDto.companyId();
    }

    // 업체 사용자 - 유저 아이디로 업체 정보 조회해오기
    // 유효성 검증 수행
    if (UserRole.ROLE_COMPANY == userInfo.role()) {

    }
    Product product = productRepository.save(applicationRequestDto.toEntity());
    return product.getId();
  }

  @Transactional
  @Override
  public UpdateProductApplicationResponseDto updateProductById(CurrentUserInfoDto userInfo,
      UUID productId, UpdateProductApplicationRequestDto requestDto) {

    Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
        .orElseThrow(() -> new CustomException(ProductErrorCode.INVALID_ID));

    if (UserRole.ROLE_COMPANY == userInfo.role()) {
      this.checkIfValidAccessToModify(userInfo.userId(), product.getCompanyId());
    }
    product.updateContent(requestDto.name(), requestDto.price(), requestDto.quantity(), requestDto.description());
    return UpdateProductApplicationResponseDto.from(product);
  }

  @Transactional
  @Override
  public void deleteProductById(CurrentUserInfoDto userInfo, UUID productId) {

    Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
        .orElseThrow(() -> new CustomException(ProductErrorCode.INVALID_ID));

    if (UserRole.ROLE_COMPANY == userInfo.role()) {
      this.checkIfValidAccessToModify(userInfo.userId(), product.getCompanyId());
    }
    LocalDateTime localDateTime = LocalDateTime.now();
    product.delete(localDateTime, userInfo.userId());
  }

  @Override
  public GetProductsApplicationResponseDto getProductList(Set<UUID> ids) {

    List<Product> products = productRepository.findByIdInAndDeletedAtIsNull(ids);
    return GetProductsApplicationResponseDto.from(products);
  }

  @Transactional
  @Override
  public UpdateStocksApplicationResponseDto updateProductStocks(
      UpdateStocksApplicationRequestDto applicationRequestDto) {

    Map<UUID, Product> productsMap = getProductsMap(applicationRequestDto);

    UpdateStocksApplicationResponseDto applicationResponseDto =
        UpdateStocksApplicationResponseDto.newInstance();
    for (UpdateStockApplicationRequestDto requestDto : applicationRequestDto.updateStockRequests()) {

      UUID productId = requestDto.id();
      Product product = productsMap.get(productId);
      boolean result = product.updateStock(requestDto.quantity());
      if (!result) {
        throw new CustomException(ProductErrorCode.NOT_ENOUGH_STOCK);
      }
      applicationResponseDto.add(UpdateStockApplicationResponseDto.of(productId, result));
    }
    return applicationResponseDto;
  }

  private Map<UUID, Product> getProductsMap(
      UpdateStocksApplicationRequestDto applicationRequestDto) {
    Set<UUID> productIds = applicationRequestDto.updateStockRequests().stream()
        .map(UpdateStockApplicationRequestDto::id).collect(Collectors.toSet());
    Map<UUID, Product> productsMap = productRepository.findByIdInAndDeletedAtIsNull(productIds)
        .stream()
        .collect(Collectors.toMap(
            Product::getId,
            Function.identity()
        ));
    return productsMap;
  }

  private void checkIfValidAccessToModify(Long userId, UUID companyId) {
//    // 업체 정보 조회해오기
//    // companyResponse
//    if (!companyResponse.getId().equals(companyId)) {
//      throw new CustomException(ProductErrorCode.FORBIDDEN_ACCESS);
//    }
  }
}
