package com.carshop.api.repository;

import com.carshop.api.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.conversationId = :conversationId ORDER BY m.createdAt ASC")
    List<Message> findByConversationId(Long conversationId);
}

