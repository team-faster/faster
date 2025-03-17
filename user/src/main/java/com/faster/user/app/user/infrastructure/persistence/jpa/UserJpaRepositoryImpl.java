package com.faster.user.app.user.infrastructure.persistence.jpa;

import com.faster.user.app.user.domain.entity.QUser;
import com.faster.user.app.user.infrastructure.persistence.jpa.dto.QUserProjection;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class UserJpaRepositoryImpl implements UserRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<QUserProjection> searchUsers(String username, String name, String slackId, Pageable pageable) {
    QUser user = QUser.user;

    BooleanBuilder builder = new BooleanBuilder();
    if (username != null && !username.isEmpty()) {
      builder.and(user.username.containsIgnoreCase(username));
    }
    if (name != null && !name.isEmpty()) {
      builder.and(user.name.containsIgnoreCase(name));
    }
    if (slackId != null && !slackId.isEmpty()) {
      builder.and(user.slackId.containsIgnoreCase(slackId));
    }

    if (!builder.hasValue()) {
      builder.and(user.id.isNotNull());
    }

    // 데이터 조회
    List<QUserProjection> results = queryFactory
        .select(Projections.fields(QUserProjection.class,
            user.id.as("id"),
            user.username.as("username"),
            user.name.as("name"),
            user.slackId.as("slackId"),
            user.role.as("role"),
            user.createdAt.as("createdAt"),
            user.createdBy.as("createdBy"),
            user.updatedAt.as("updatedAt"),
            user.updatedBy.as("updatedBy"),
            user.deletedAt.as("deletedAt"),
            user.deletedBy.as("deletedBy")
        ))
        .from(user)
        .where(builder)
        .orderBy(user.createdAt.desc()) // 최신순 정렬
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    // 전체 개수 조회
    long total = queryFactory
        .select(user.count())
        .from(user)
        .where(builder)
        .fetchOne();

    return new PageImpl<>(results, pageable, total);
  }
}