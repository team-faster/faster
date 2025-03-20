package com.faster.message.app.message.infrastructure.persistence.jpa;

import com.faster.message.app.message.domain.entity.Message;
import com.faster.message.app.message.domain.repository.MessageRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageJpaRepository extends JpaRepository<Message, Long>, MessageRepository {
}
