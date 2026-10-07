package com.example.CreatorHub.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.CreatorHub.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRecipientIdOrderByCreatedAtDesc(Long recipientId);
    void deleteByRecipientId(Long recipientId);
}	

