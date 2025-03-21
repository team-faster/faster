package com.faster.product.app.product.application.dto.response;

import com.common.exception.CustomException;
import com.faster.product.app.global.exception.ProductErrorCode;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetCompanyApplicationResponseDto(
    UUID companyId,
    UUID hubId,
    Long companyManagerUserId,
    String name,
    String contact,
    String address,
    CompanyType type,
    String companyManagerSlackId,
    String companyManagerName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public enum CompanyType {
      SUPPLIER, RECEIVER;

      public static CompanyType fromString(String type) {
        try {
          return type == null ? null : valueOf(type);
        } catch (IllegalArgumentException e) {
          throw new CustomException(ProductErrorCode.INVALID_TYPE);
        }
      }
    }
}
