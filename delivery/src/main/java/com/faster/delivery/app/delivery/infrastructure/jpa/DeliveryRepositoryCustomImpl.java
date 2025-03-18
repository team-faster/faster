package com.faster.delivery.app.delivery.infrastructure.jpa;

import static com.faster.delivery.app.delivery.domain.entity.QDelivery.delivery;
import static com.faster.delivery.app.delivery.domain.entity.QDeliveryRoute.deliveryRoute;

import com.faster.delivery.app.delivery.domain.entity.Delivery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeliveryRepositoryCustomImpl implements DeliveryRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public Optional<Delivery> findByDeliveryId(UUID targetDeliveryId) {
    return Optional.ofNullable(
        queryFactory.select(delivery)
            .from(delivery)
            .join(delivery.deliveryRouteList, deliveryRoute).fetchJoin()
            .where(
                delivery.id.eq(targetDeliveryId)
                    .and(delivery.deletedAt.isNull())
            )
            .fetchOne()
    );
  }
}
