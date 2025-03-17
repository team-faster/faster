package com.faster.order.app.order.presentation.dto.request;

import com.faster.order.app.order.application.dto.request.SaveOrderApplicationRequestDto;
import com.faster.order.app.order.application.dto.request.SaveOrderApplicationRequestDto.SaveOrderItemApplicationRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record SaveOrderRequestDto(
    UUID supplierCompanyId,
    String supplierCompanyName,
    UUID receivingCompanyId,
    String receivingCompanyName,
    String receivingCompanyAddress,
    String receivingCompanyContact,
    String request,
    @NotEmpty @Valid List<SaveOrderItemRequestDto> orderItems
) {

  public SaveOrderApplicationRequestDto toApplicationRequestDto() {
    return SaveOrderApplicationRequestDto.builder()
        .supplierCompanyId(this.supplierCompanyId)
        .supplierCompanyName(this.supplierCompanyName)
        .receivingCompanyId(this.receivingCompanyId)
        .receivingCompanyName(this.receivingCompanyName)
        .receivingCompanyAddress(this.receivingCompanyAddress)
        .receivingCompanyContact(this.receivingCompanyContact)
        .request(this.request)
        .orderItems(this.orderItems.stream()
            .map(SaveOrderItemRequestDto::toApplicationRequestDto)
            .toList())
        .build();
  }

  record SaveOrderItemRequestDto(
      @NotNull UUID productId,
      @NotBlank String name,
      @PositiveOrZero @Digits(integer = 10, fraction = 2)
      BigDecimal price,
      @Min(0) Integer quantity
  ) {

    SaveOrderItemApplicationRequestDto toApplicationRequestDto() {
      return SaveOrderItemApplicationRequestDto.builder()
          .productId(productId)
          .name(name)
          .price(price)
          .quantity(quantity)
          .build();
    }
  }
}
