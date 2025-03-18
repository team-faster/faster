package com.faster.product.app.product.application.usecase;

import com.common.exception.CustomException;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.faster.product.app.global.exception.ProductErrorCode;
import com.faster.product.app.product.application.CompanyClient;
import com.faster.product.app.product.application.dto.request.GetProductsApplicationResponseDto;
import com.faster.product.app.product.application.dto.request.UpdateStocksApplicationRequestDto;
import com.faster.product.app.product.application.dto.request.UpdateProductApplicationRequestDto;
import com.faster.product.app.product.application.dto.response.GetCompanyApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.UpdateStocksApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.UpdateStocksApplicationResponseDto.UpdateStockApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.GetProductDetailApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.UpdateProductApplicationResponseDto;
import com.faster.product.app.product.application.dto.request.SaveProductApplicationRequestDto;
import com.faster.product.app.product.domain.entity.Product;
import com.faster.product.app.product.domain.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
  private final CompanyClient companyClient;

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

    // 업체 유효성 검증 수행
    GetCompanyApplicationResponseDto companyDto =
        getCompanyDtoByRole(userInfo, applicationRequestDto.companyId());
    this.isValidCompany(companyDto);

    Product product = productRepository.save(applicationRequestDto.toEntity());
    return product.getId();
  }

  @Transactional
  @Override
  public UpdateProductApplicationResponseDto updateProductById(CurrentUserInfoDto userInfo,
      UUID productId, UpdateProductApplicationRequestDto requestDto) {

    Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
        .orElseThrow(() -> new CustomException(ProductErrorCode.INVALID_ID));

    this.checkIfValidAccessToModify(userInfo, product.getCompanyId());
    product.updateContent(requestDto.name(), requestDto.price(), requestDto.quantity(), requestDto.description());
    return UpdateProductApplicationResponseDto.from(product);
  }

  @Transactional
  @Override
  public void deleteProductById(CurrentUserInfoDto userInfo, UUID productId) {

    Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
        .orElseThrow(() -> new CustomException(ProductErrorCode.INVALID_ID));

    this.checkIfValidAccessToModify(userInfo, product.getCompanyId());
    product.softDelete(userInfo.userId());
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

    Map<UUID, Integer> updateStocksMap = applicationRequestDto.toUpdateStockMap();
    Map<UUID, Product> productsMap = getProductsMap(applicationRequestDto);

    List<UpdateStockApplicationResponseDto> applicationResponses = new ArrayList<>();
    for (Entry<UUID, Integer> updateStock : updateStocksMap.entrySet()) {
      UUID productId = updateStock.getKey();
      Product product = productsMap.get(productId);
      boolean result = product.updateStock(updateStock.getValue());
      if (!result) {
        throw new CustomException(ProductErrorCode.NOT_ENOUGH_STOCK);
      }
      applicationResponses.add(UpdateStockApplicationResponseDto.of(productId, result));
    }
    return UpdateStocksApplicationResponseDto.from(applicationResponses);
  }

  private Map<UUID, Product> getProductsMap(
      UpdateStocksApplicationRequestDto applicationRequestDto) {

    Set<UUID> productIds = applicationRequestDto.getProductIdsSet();
    Map<UUID, Product> productsMap = productRepository.findByIdInAndDeletedAtIsNull(productIds)
        .stream()
        .collect(Collectors.toMap(
            Product::getId,
            Function.identity()
        ));
    return productsMap;
  }

  private GetCompanyApplicationResponseDto getCompanyDtoByRole(CurrentUserInfoDto userInfo, UUID companyId) {

    // 업체 담당자
    if (UserRole.ROLE_COMPANY == userInfo.role()) {
      return companyClient.getCompanyByCompanyManagerId(userInfo.userId());
    }
    // 마스터 사용자
    return companyClient.getCompanyByCompanyId(companyId);
  }

  private void isValidCompany(GetCompanyApplicationResponseDto companyDto) {
    //todo GetCompanyApplicationResponseDto 에 Company Type 이넘 만들기
    if (!"SUPPLIER".equals(companyDto.type())) {
      throw new CustomException(ProductErrorCode.NOT_SUPPLIER);
    }
  }

  private void checkIfValidAccessToModify(CurrentUserInfoDto userInfo, UUID companyId) {

    if (UserRole.ROLE_MASTER == userInfo.role()) {
      return;
    }
    // 업체 정보 조회해오기
    GetCompanyApplicationResponseDto companyDto =
        companyClient.getCompanyByCompanyManagerId(userInfo.userId());

    if (!companyDto.id().equals(companyId)) {
      throw new CustomException(ProductErrorCode.FORBIDDEN_ACCESS);
    }
  }
}
