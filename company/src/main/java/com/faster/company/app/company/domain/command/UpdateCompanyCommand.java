package com.faster.company.app.company.domain.command;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateCompanyCommand(
    UUID hubId,
    String name,
    String contact,
    String address
) {

}
