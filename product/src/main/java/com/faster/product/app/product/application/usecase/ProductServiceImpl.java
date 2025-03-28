package com.faster.product.app.product.application.usecase;

import com.common.exception.CustomException;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.PageResponse;
import com.faster.product.app.global.exception.ProductErrorCode;
import com.faster.product.app.product.application.client.CompanyClient;
import com.faster.product.app.product.application.client.HubClient;
import com.faster.product.app.product.application.dto.request.GetProductsApplicationResponseDto;
import com.faster.product.app.product.application.dto.request.SearchProductConditionDto;
import com.faster.product.app.product.application.dto.request.SortedUpdateStocksApplicationRequestDto;
import com.faster.product.app.product.application.dto.request.SortedUpdateStocksApplicationRequestDto.UpdateStockApplicationRequestDto;
import com.faster.product.app.product.application.dto.request.UpdateProductApplicationRequestDto;
import com.faster.product.app.product.application.dto.request.UpdateProductHubApplicationRequestDto;
import com.faster.product.app.product.application.dto.response.GetCompanyApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.GetCompanyApplicationResponseDto.CompanyType;
import com.faster.product.app.product.application.dto.response.GetHubsApplicationResponseDto.HubInfo;
import com.faster.product.app.product.application.dto.response.SearchProductApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.UpdateProductHubApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.UpdateStocksApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.UpdateStocksApplicationResponseDto.UpdateStockApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.GetProductDetailApplicationResponseDto;
import com.faster.product.app.product.application.dto.response.UpdateProductApplicationResponseDto;
import com.faster.product.app.product.application.dto.request.SaveProductApplicationRequestDto;
import com.faster.product.app.product.domain.entity.Product;
import com.faster.product.app.product.domain.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
  private final CompanyClient companyClient;
  private final HubClient hubClient;

  @Override
  public PageResponse<SearchProductApplicationResponseDto> getProductsByCondition(CurrentUserInfoDto userInfo,
      Pageable pageable, SearchProductConditionDto condition) {
    // 1. 마스터 - 모든 주문 조회 가능
    // 2. 업체 담당자 - 해당 업체 주문만 조회 가능
    //GetCompanyApplicationResponseDto company = getCompanyDtoByUser(userInfo);
    UUID companyId = null; //company == null ? null : company.companyId();

    Page<SearchProductApplicationResponseDto> pageList =
        productRepository.getProductsByConditionAndCompanyId(
            pageable, condition.toCriteria(), companyId, userInfo.role())
        .map(SearchProductApplicationResponseDto::from);
    return PageResponse.from(pageList);
  }

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

    // 허브 유효성 검증 수행
    HubInfo hubDto = getHubById(applicationRequestDto.hubId(), ProductErrorCode.INVALID_HUB_ID);
    // 업체 유효성 검증 수행
    GetCompanyApplicationResponseDto companyDto =
        this.getAndCheckIfValidAccessToModify(userInfo, applicationRequestDto.companyId());
    this.isSupplierCompany(companyDto);
    this.checkIfMatchedCompanyHub(hubDto, companyDto);

    Product product = productRepository.save(applicationRequestDto.toEntity());
    return product.getId();
  }

  @Transactional
  @Override
  public UpdateProductApplicationResponseDto updateProductById(CurrentUserInfoDto userInfo,
      UUID productId, UpdateProductApplicationRequestDto requestDto) {

    Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
        .orElseThrow(() -> new CustomException(ProductErrorCode.INVALID_ID));
    this.getAndCheckIfValidAccessToModify(userInfo, product.getCompanyId());

    product.updateContent(requestDto.name(), requestDto.price(), requestDto.quantity(), requestDto.description());
    return UpdateProductApplicationResponseDto.from(product);
  }

  @Transactional
  @Override
  public void deleteProductById(CurrentUserInfoDto userInfo, UUID productId) {

    Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
        .orElseThrow(() -> new CustomException(ProductErrorCode.INVALID_ID));
    this.getAndCheckIfValidAccessToModify(userInfo, product.getCompanyId());

    product.softDelete(userInfo.userId());
  }

  @Override
  public GetProductsApplicationResponseDto getProductList(Set<UUID> ids) {

    List<Product> products = productRepository.findByIdInAndDeletedAtIsNull(ids);
    return GetProductsApplicationResponseDto.from(products);
  }

  @Transactional
  @Override
  public UpdateStocksApplicationResponseDto updateProductStocksInternal(
      SortedUpdateStocksApplicationRequestDto updateStocksDto) {

    List<UpdateStockApplicationResponseDto> applicationResponses = new ArrayList<>();
    for (UpdateStockApplicationRequestDto requestDto : updateStocksDto.sortedUpdateStockRequests()) {

      UUID productId = requestDto.id();
      boolean result = this.processUpdateStock(productId, requestDto.quantity());
      applicationResponses.add(UpdateStockApplicationResponseDto.of(productId, result));
    }
    return UpdateStocksApplicationResponseDto.from(applicationResponses);
  }

  @Transactional
  @Override
  public UpdateProductHubApplicationResponseDto updateProductHubByCompanyIdInternal(
      CurrentUserInfoDto userInfo, UpdateProductHubApplicationRequestDto applicationDto) {

    productRepository.updateProductHubByCompanyId(
        applicationDto.companyId(), applicationDto.hubId(), userInfo.userId());
    return UpdateProductHubApplicationResponseDto.of(applicationDto.companyId(), applicationDto.hubId());
  }

  @Transactional
  @Override
  public void deleteProductByCompanyIdInternal(CurrentUserInfoDto userInfo, UUID companyId) {

    productRepository.deleteProductByCompanyId(companyId, userInfo.userId());
  }

  private boolean processUpdateStock(UUID productId, Integer quantity) {

    Product product = productRepository.findByIdAndDeletedAtIsNullWithPessimisticLock(productId)
        .orElseThrow(() -> new CustomException(ProductErrorCode.INVALID_ID));
    boolean result = product.updateStock(quantity);
    if (!result) {
      throw new CustomException(ProductErrorCode.NOT_ENOUGH_STOCK);
    }
    return result;
  }

  private GetCompanyApplicationResponseDto getAndCheckIfValidAccessToModify(
      CurrentUserInfoDto userInfo, UUID companyId) {

    // 업체 정보 조회해오기
    GetCompanyApplicationResponseDto companyDto = getCompanyDtoByRole(userInfo, companyId);

    if (companyDto == null || !companyDto.companyId().equals(companyId)) {
      throw new CustomException(ProductErrorCode.FORBIDDEN_ACCESS);
    }
    return companyDto;
  }

  private GetCompanyApplicationResponseDto getCompanyDtoByRole(CurrentUserInfoDto userInfo, UUID companyId) {

    // 업체 담당자
    if (UserRole.ROLE_COMPANY == userInfo.role()) {
      return companyClient.getCompanyByCompanyManagerId(userInfo.userId());
    }
    // 마스터 사용자
    if (UserRole.ROLE_MASTER == userInfo.role()) {
      return companyClient.getCompanyByCompanyId(companyId);
    }
    return null;
  }

  private GetCompanyApplicationResponseDto getCompanyDtoByUser(CurrentUserInfoDto userInfo) {

    if (UserRole.ROLE_MASTER == userInfo.role()) {
      return null;
    }
    if (UserRole.ROLE_COMPANY == userInfo.role()) {
      return companyClient.getCompanyByCompanyManagerId(userInfo.userId());
    }
    return null;
  }

  private HubInfo getHubById(UUID hubId, ProductErrorCode errorCode) {

    return Optional.ofNullable(hubClient.getHubById(hubId))
        .filter(hub -> !CollectionUtils.isEmpty(hub.hubInfos()))
        .flatMap(hub -> hub.hubInfos().stream().findFirst())
        .orElseThrow(() -> new CustomException(errorCode));
  }

  private void isSupplierCompany(GetCompanyApplicationResponseDto companyDto) {

    if (CompanyType.SUPPLIER != companyDto.type()) {
      throw new CustomException(ProductErrorCode.NOT_SUPPLIER);
    }
  }

  private static void checkIfMatchedCompanyHub(HubInfo hubDto, GetCompanyApplicationResponseDto companyDto) {

    if (!hubDto.id().equals(companyDto.hubId())) {
      throw new CustomException(ProductErrorCode.INVALID_REQUEST);
    }
  }
}
