package com.faster.delivery.app.deliverymanager.application.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.faster.delivery.app.config.TearDownExecutor;
import com.faster.delivery.app.deliverymanager.application.HubClient;
import com.faster.delivery.app.deliverymanager.application.UserClient;
import com.faster.delivery.app.deliverymanager.application.dto.DeliveryManagerSaveDto;
import com.faster.delivery.app.deliverymanager.application.dto.HubDto;
import com.faster.delivery.app.deliverymanager.application.dto.UserDto;
import com.faster.delivery.app.deliverymanager.application.type.DeliveryManagerType;
import com.faster.delivery.app.deliverymanager.domain.entity.DeliveryManager;
import com.faster.delivery.app.deliverymanager.domain.repository.DeliveryManagerRepository;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@Import({TearDownExecutor.class})
@ActiveProfiles("test")
@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
class DeliveryManagerFacadeTest {

  @Autowired
  private DeliveryManagerFacade deliveryManagerFacade;

  @Autowired
  private DeliveryManagerRepository deliveryManagerRepository;

  @Autowired
  private TearDownExecutor tearDownExecutor;
  @MockitoBean
  private UserClient userClient;
  @MockitoBean
  private HubClient hubClient;

  @AfterEach
  void tearDown() {
    tearDownExecutor.execute();
  }

  @DisplayName("배송 매니저를 저장한다.")
  @Test
  void saveDeliveryManager() {
    // given
    UUID hubId = UUID.randomUUID();
    Long userId = 1L;
    DeliveryManagerType type = DeliveryManagerType.COMPANY_DELIVERY;

    String user1Name = "유저1이름";
    UserDto user1 = UserDto.builder()
        .userId(userId)
        .username("유저1")
        .slackId("slack1@gmail.com")
        .name(user1Name)
        .role("ROLE_DELIVER")
        .build();

    HubDto hubDto = HubDto.builder()
        .hubId(hubId)
        .build();

    // stubbing
    when(userClient.getUserData(any(Long.class))).thenReturn(user1);
    when(hubClient.getHubListData(any(List.class))).thenReturn(List.of(hubDto));

    // when
    deliveryManagerFacade.saveDeliveryManager(DeliveryManagerSaveDto.builder()
        .hubId(hubId)
        .type(type.name())
        .userId(userId)
        .build());

    // then
    List<DeliveryManager> all = deliveryManagerRepository.findAll();
    assertThat(all).hasSize(1)
        .extracting("userName", "type", "deliverySequenceNumber")
        .containsExactlyInAnyOrder(
            tuple(user1Name, DeliveryManager.Type.COMPANY_DELIVERY, 1) // 정확한 enum 값 사용
        );
  }

  @DisplayName("배송 매니저는 허브id별 시퀀스가 순차적으로 올라간다.")
  @Test
  public void saveDeliveryManager_withRock2() throws InterruptedException {
    int threadCount = 100;

    // given
    UUID hubId = UUID.randomUUID();
    ExecutorService executorService = Executors.newFixedThreadPool(32);
    DeliveryManagerType type = DeliveryManagerType.COMPANY_DELIVERY;

    // stubbing
    for (int i = 0; i < threadCount; i++) {
      long userId = Long.valueOf(i);
      UserDto user = UserDto.builder()
          .userId(Long.valueOf(userId))
          .username("유저1" + userId)
          .slackId("slack@gmail.com" + userId)
          .name("userName" + userId)
          .role("ROLE_MASTER")
          .build();
      when(userClient.getUserData(eq(Long.valueOf(i)))).thenReturn(user);
    }

    HubDto hubDto = HubDto.builder()
        .hubId(hubId)
        .build();
    when(hubClient.getHubListData(any(List.class))).thenReturn(List.of(hubDto));

    // when
    List<CompletableFuture<Void>> futures = IntStream.range(0, threadCount)
        .mapToObj(i -> CompletableFuture.runAsync(() -> {
          deliveryManagerFacade.saveDeliveryManager(DeliveryManagerSaveDto.builder()
              .hubId(hubId)
              .type(type.name())
              .userId(Long.valueOf(i))
              .build());
        }, executorService))
        .collect(Collectors.toList());

    // 모든 작업이 끝날 때까지 기다림
    futures.forEach(CompletableFuture::join);

    // ExecutorService 종료
    executorService.shutdown();

    // then
    List<DeliveryManager> all = deliveryManagerRepository.findAll();
    assertThat(all).hasSize(threadCount);
    List<Integer> sequenceNumbers = all.stream()
        .map(DeliveryManager::getDeliverySequenceNumber)
        .sorted()
        .toList();

    assertThat(sequenceNumbers)
        .containsExactlyElementsOf(IntStream.rangeClosed(1, threadCount).boxed().toList());
  }
}