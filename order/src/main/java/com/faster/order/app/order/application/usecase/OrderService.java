package com.faster.order.app.order.application.usecase;

import com.common.resolver.dto.CurrentUserInfoDto;
import com.common.response.PageResponse;
import com.faster.order.app.order.application.dto.request.SearchOrderConditionDto;
import com.faster.order.app.order.application.dto.response.OrderDetailApplicationResponseDto;
import com.faster.order.app.order.application.dto.response.SearchOrderApplicationResponseDto;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface OrderService {

  OrderDetailApplicationResponseDto getOrderById(CurrentUserInfoDto userInfo, UUID orderId);

  void deleteOrderById(CurrentUserInfoDto userInfo, UUID orderId);

  PageResponse<SearchOrderApplicationResponseDto> getOrdersByCondition(CurrentUserInfoDto userInfo,
      Pageable pageable, SearchOrderConditionDto condition);
}
