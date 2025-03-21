package com.faster.message.app.message.application.client;

import com.faster.message.app.message.application.dto.response.AGetHubResponseDto;
import java.util.List;
import java.util.UUID;

public interface HubClient {

  AGetHubResponseDto getOrderByOrderId(List<UUID> hubId);

}
