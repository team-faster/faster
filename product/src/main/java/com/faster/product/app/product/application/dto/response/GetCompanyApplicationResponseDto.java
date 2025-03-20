package com.faster.product.app.product.application.dto.response;

import com.common.exception.CustomException;
import com.faster.product.app.global.exception.ProductErrorCode;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetCompanyApplicationResponseDto(
    UUID id,
    UUID hubId,
    UUID companyManagerId,
    String name,
    String contact,
    String address,
    Type type
) {

    public enum Type {
      SUPPLIER, RECEIVER;

      public static Type fromString(String type) {
        try {
          return type == null ? null : valueOf(type);
        } catch (IllegalArgumentException e) {
          throw new CustomException(ProductErrorCode.INVALID_TYPE);
        }
      }
    }
}
