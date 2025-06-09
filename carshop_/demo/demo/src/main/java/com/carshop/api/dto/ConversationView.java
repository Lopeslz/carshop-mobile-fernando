package com.carshop.api.dto;

import java.time.LocalDateTime;

public class ConversationView {
    private Long conversationId;
    private String carTitle;
    private String carImageUrl;
    private String sellerName;
    private String carPrice;
    private LocalDateTime lastMessageAt;

    public ConversationView(Long conversationId, String carTitle, String carImageUrl,
                            String sellerName, String carPrice, LocalDateTime lastMessageAt) {
        this.conversationId = conversationId;
        this.carTitle = carTitle;
        this.carImageUrl = carImageUrl;
        this.sellerName = sellerName;
        this.carPrice = carPrice;
        this.lastMessageAt = lastMessageAt;
    }

    // Getters e setters (ou use Lombok @Getter @Setter)
    public Long getConversationId() { return conversationId; }
    public String getCarTitle() { return carTitle; }
    public String getCarImageUrl() { return carImageUrl; }
    public String getSellerName() { return sellerName; }
    public String getCarPrice() { return carPrice; }
    public LocalDateTime getLastMessageAt() { return lastMessageAt; }
}
