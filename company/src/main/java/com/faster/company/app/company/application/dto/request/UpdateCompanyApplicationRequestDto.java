package com.faster.company.app.company.application.dto.request;

import com.faster.company.app.company.domain.command.UpdateCompanyCommand;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateCompanyApplicationRequestDto(
    UUID companyId,
    UUID hubId,
    String name,
    String contact,
    String address
) {

  public UpdateCompanyCommand toCommand() {
    return UpdateCompanyCommand.builder()
        .hubId(hubId)
        .name(name)
        .contact(contact)
        .address(address)
        .build();
  }
}
