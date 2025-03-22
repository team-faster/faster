package com.faster.message.app.message.infrastructure.persistence.jpa;

import static com.faster.message.app.message.domain.entity.QMessage.message;

import com.faster.message.app.message.domain.entity.Message;
import com.faster.message.app.message.domain.enums.MessageType;
import com.faster.message.app.message.presentation.dto.response.PGetAllMessageResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class MessageJpaRepositoryImpl implements MessageRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<PGetAllMessageResponseDto> searchMessages(
      String targetSlackId,
      String content,
      String messageType,
      LocalDate sendAt,
      int page,
      int size) {
    Pageable pageable = PageRequest.of(page, size);

    List<Message> messages = queryFactory.selectFrom(message)
        .where(
            targetSlackIdEq(targetSlackId),
            contentContains(content),
            messageTypeEq(messageType),
            sendAtDateEq(sendAt)
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = queryFactory.select(message.count())
        .from(message)
        .where(
            targetSlackIdEq(targetSlackId),
            contentContains(content),
            messageTypeEq(messageType),
            sendAtDateEq(sendAt)
        )
        .fetchOne();

    if (total == null) {
      total = 0L;
    }

    List<PGetAllMessageResponseDto> result = messages.stream().map(msg -> new PGetAllMessageResponseDto(
        msg.getId(),
        msg.getTargetSlackId(),
        msg.getContent(),
        msg.getMessageType().name(),
        msg.getSendAt()
    )).toList();

    return new PageImpl<>(result, pageable, total);
  }

  private BooleanExpression targetSlackIdEq(String targetSlackId) {
    return targetSlackId != null ? message.targetSlackId.eq(targetSlackId) : null;
  }

  private BooleanExpression contentContains(String content) {
    return (content != null && !content.trim().isEmpty()) ? message.content.containsIgnoreCase(content) : null;
  }

  private BooleanExpression messageTypeEq(String messageType) {
    return messageType != null ? message.messageType.eq(MessageType.valueOf(messageType)) : null;
  }

  private BooleanExpression sendAtDateEq(LocalDate sendAt) {
    if (sendAt == null) {
      return null;
    }
    LocalDateTime startOfDay = sendAt.atStartOfDay();
    LocalDateTime endOfDay = sendAt.atTime(LocalTime.MAX);
    return message.sendAt.between(startOfDay, endOfDay);
  }
}
