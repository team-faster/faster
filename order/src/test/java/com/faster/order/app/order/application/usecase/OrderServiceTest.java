package com.faster.order.app.order.application.usecase;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.resolver.dto.UserRole;
import com.common.response.PageResponse;
import com.faster.order.app.order.application.dto.request.SearchOrderConditionDto;
import com.faster.order.app.order.application.dto.response.SearchOrderApplicationResponseDto;
import com.faster.order.app.order.domain.entity.Order;
import com.faster.order.app.order.domain.enums.OrderStatus;
import com.faster.order.app.order.domain.repository.OrderRepository;
import com.faster.order.app.order.fixture.OrderFixture;
import com.faster.order.app.order.utils.DatabaseCleanUp;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@Import(DatabaseCleanUp.class)
@SpringBootTest
@ActiveProfiles("test")
class OrderServiceTest {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  DatabaseCleanUp databaseCleanUp;

  @BeforeEach
  void setUp() {

    var supplierCompanyId = UUID.randomUUID();
    var receivingCompanyId = UUID.randomUUID();
    var deliveryId = UUID.randomUUID();

    List<Order> orders = IntStream.range(0, 10)
        .mapToObj(i -> OrderFixture.createOrder(supplierCompanyId, receivingCompanyId,
            deliveryId, 10)
        )
        .toList();
    orderRepository.saveAll(orders);
  }

  @AfterEach
  void tearDown() {
    databaseCleanUp.afterPropertiesSet();
    databaseCleanUp.execute();
  }

  @Test
  void getOrdersByCondition() {
    //given
    CurrentUserInfoDto userInfo = new CurrentUserInfoDto(1L, UserRole.ROLE_MASTER);
    PageRequest pageRequest = PageRequest.of(0, 10);

    //when
    PageResponse<SearchOrderApplicationResponseDto> orders
        = orderService.getOrdersByCondition(
        userInfo, pageRequest, SearchOrderConditionDto.of(
            BigDecimal.valueOf(1000), null, null, null,
            null, null, null, OrderStatus.ACCEPTED,
            null, null, null));

    //then
    Assertions.assertThat(orders.contents().size()).isEqualTo(10);
  }
}