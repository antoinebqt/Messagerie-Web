package fr.teama.datasaverservice.components.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class MessageDTO {
    private UUID id;
    private UUID chatId;
    private UUID snapUserId;
    private String text;
    private Integer lifespan;
    private LocalDateTime readTime;
    private LocalDateTime creationDate;

    public MessageDTO() {

    }

    @Override
    public String toString() {
        return "{ " +
                "id: " + id +
                ", chat_id: " + chatId +
                ", snapUserId: " + snapUserId +
                ", text: " + text +
                ", lifespan: " + lifespan +
                ", readTime: " + readTime +
                ", creationDate: " + creationDate +
                " }";
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UUID getSnapUserId() {
        return snapUserId;
    }

    public void setSnapUserId(UUID snapUserId) {
        this.snapUserId = snapUserId;
    }

    public Integer getLifespan() {
        return lifespan;
    }

    public void setLifespan(Integer lifespan) {
        this.lifespan = lifespan;
    }

    public LocalDateTime getReadTime() {
        return readTime;
    }

    public void setReadTime(LocalDateTime isRead) {
        this.readTime = isRead;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
