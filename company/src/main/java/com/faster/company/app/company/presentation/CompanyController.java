package com.faster.company.app.company.presentation;

import com.common.aop.annotation.AuthCheck;
import com.common.resolver.annotation.CurrentUserInfo;
import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.ApiResponse;
import com.common.response.PageResponse;
import com.faster.company.app.company.application.dto.request.GetCompaniesApplicationRequestDto;
import com.faster.company.app.company.application.dto.response.GetCompaniesApplicationResponseDto;
import com.faster.company.app.company.application.dto.response.GetCompanyApplicationResponseDto;
import com.faster.company.app.company.application.dto.response.UpdateCompanyApplicationResponseDto;
import com.faster.company.app.company.application.usecase.CompanyService;
import com.faster.company.app.company.presentation.dto.request.SaveCompanyRequestDto;
import com.faster.company.app.company.presentation.dto.request.UpdateCompanyRequestDto;
import com.faster.company.app.company.presentation.dto.response.GetCompaniesResponseDto;
import com.faster.company.app.company.presentation.dto.response.GetCompanyResponseDto;
import com.faster.company.app.company.presentation.dto.response.UpdateCompanyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "업체", description = "업체 생성 및 수정")
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@RestController
public class CompanyController {

  private final CompanyService companyService;

  @Operation(summary = "업체 조회", description = "배송 저장 API 입니다.")
  @GetMapping("/{companyId}")
  public ResponseEntity<ApiResponse<GetCompanyResponseDto>> getCompanyById(
      @PathVariable UUID companyId) {

    GetCompanyApplicationResponseDto companyDto = companyService.getCompanyById(companyId);
    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "업체 조회가 성공적으로 수행되었습니다.",
            HttpStatus.OK.value(),
            GetCompanyResponseDto.from(companyDto)));
  }

  @Operation(summary = "모든 업체 조회", description = "모든 업체 조회 API 입니다.")
  @GetMapping
  public ResponseEntity<ApiResponse<PageResponse<GetCompaniesResponseDto>>> getCompanies(
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
          @SortDefault(sort = "updatedAt", direction = Sort.Direction.DESC)
      }) Pageable pageable,
      @RequestParam(name = "hub-id", required = false) UUID hubId,
      @RequestParam(name = "company-manager-id", required = false) Long companyManagerId,
      @RequestParam(name = "search-text", required = false) String searchText,
      @RequestParam(name = "name-search-text", required = false) String nameSearchText,
      @RequestParam(name = "contact-search-text", required = false) String contactSearchText,
      @RequestParam(name = "address-search-text", required = false) String addressSearchText,
      @RequestParam(name = "type-text", required = false) String type
  ){
    PageResponse<GetCompaniesApplicationResponseDto> companies =
        companyService.getCompanies(GetCompaniesApplicationRequestDto.of(
            pageable,
            hubId,
            companyManagerId,
            searchText,
            nameSearchText,
            contactSearchText,
            addressSearchText,
            type));

    return ResponseEntity.ok(ApiResponse.ok(companies.map(GetCompaniesResponseDto::from)));
  }

  @Operation(summary = "업체 저장", description = "업체 저장 API 입니다.")
  @AuthCheck(roles = {UserRole.ROLE_MASTER, UserRole.ROLE_COMPANY})
  @PostMapping
  public ResponseEntity<ApiResponse<Void>> saveCompany(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @RequestBody @Valid SaveCompanyRequestDto requestDto) {

    UUID companyId = companyService.saveCompany(userInfo, requestDto.toApplicationDto());
    return ResponseEntity.created(
            UriComponentsBuilder.fromUriString("/api/companies/{companyId}")
                .buildAndExpand(companyId)
                .toUri()
        )
        .body(new ApiResponse<>(
            "업체 생성이 성공적으로 수행되었습니다.",
            HttpStatus.OK.value(),
            null));
  }

  @Operation(summary = "업체 수정", description = "업체 수정 API 입니다.")
  @AuthCheck(roles = {UserRole.ROLE_MASTER, UserRole.ROLE_COMPANY})
  @PatchMapping
  public ResponseEntity<ApiResponse<UpdateCompanyResponseDto>> updateCompany(
      @CurrentUserInfo CurrentUserInfoDto userInfo,
      @RequestBody @Valid UpdateCompanyRequestDto requestDto) {

    UpdateCompanyApplicationResponseDto companyDto =
        companyService.updateCompany(userInfo, requestDto.toApplicationDto());
    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "업체 수정이 성공적으로 수행되었습니다.",
            HttpStatus.OK.value(),
            UpdateCompanyResponseDto.from(companyDto)));
  }

  @Operation(summary = "업체 삭제", description = "업체 삭제 API 입니다.")
  @AuthCheck(roles = {UserRole.ROLE_MASTER, UserRole.ROLE_COMPANY})
  @DeleteMapping("/{companyId}")
  public ResponseEntity<ApiResponse<UpdateCompanyResponseDto>> deleteCompany(
      @CurrentUserInfo CurrentUserInfoDto userInfo, @PathVariable UUID companyId) {

    companyService.deleteCompany(userInfo, companyId);
    return ResponseEntity.ok()
        .body(new ApiResponse<>(
            "업체 삭제가 성공적으로 수행되었습니다.",
            HttpStatus.OK.value(),
            null));
  }
}
