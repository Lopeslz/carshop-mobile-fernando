package com.carshop.api.repository;

import com.carshop.api.model.Conversation;
import com.carshop.api.dto.ConversationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("SELECT new com.carshop.api.dto.ConversationView(c.id, car.title, car.imageUrl, seller.name, car.price, c.lastMessageAt) " +
            "FROM Conversation c " +
            "JOIN Car car ON car.id = c.itemId " +
            "JOIN User seller ON seller.id = c.sellerId " +
            "WHERE c.buyerId = :userId OR c.sellerId = :userId " +
            "ORDER BY c.lastMessageAt DESC")
    List<ConversationView> getUserConversationsFullView(@Param("userId") Long userId);


    Conversation findByBuyerIdAndSellerIdAndItemId(Long buyerId, Long sellerId, Long itemId);
}
