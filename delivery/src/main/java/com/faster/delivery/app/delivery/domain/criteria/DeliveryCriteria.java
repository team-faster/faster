package com.faster.delivery.app.delivery.domain.criteria;

import com.faster.delivery.app.delivery.domain.entity.Delivery.Status;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryCriteria(
    UUID hubDeliveryManagerId,
    UUID companyDeliveryManagerId,
    UUID receiptCompanyId,
    UUID hubId,
    Status status
) {

  public static DeliveryCriteria of(
      UUID hubDeliveryManagerId, UUID companyDeliveryManagerId,
      UUID receiptCompanyId, UUID hubId, Status status) {

    return DeliveryCriteria.builder()
        .hubDeliveryManagerId(hubDeliveryManagerId)
        .companyDeliveryManagerId(companyDeliveryManagerId)
        .receiptCompanyId(receiptCompanyId)
        .hubId(hubId)
        .status(status)
        .build();
  }
}
