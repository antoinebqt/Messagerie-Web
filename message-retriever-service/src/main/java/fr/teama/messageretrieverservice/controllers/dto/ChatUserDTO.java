package fr.teama.messageretrieverservice.controllers.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ChatUserDTO {
    private UUID chatId;
    private UUID userId;
    private LocalDateTime lastMessageCreationDate;

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getLastMessageCreationDate() {
        return lastMessageCreationDate;
    }

    public void setLastMessageCreationDate(LocalDateTime lastMessageCreationDate) {
        this.lastMessageCreationDate = lastMessageCreationDate;
    }
}
