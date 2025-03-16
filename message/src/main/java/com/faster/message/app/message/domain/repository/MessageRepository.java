package com.faster.message.app.message.domain.repository;

import com.faster.message.app.message.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
