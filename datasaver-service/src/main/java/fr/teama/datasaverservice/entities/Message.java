package fr.teama.datasaverservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Message {
    @Id
    private UUID id;

    @ManyToOne
    @JsonIgnore
    private Chat chat;

    private UUID snapUserId;

    private String messageBody;

    private Integer lifespan;

    private LocalDateTime readTime;

    private LocalDateTime creationDate;

    public Message() {

    }

    public Message(UUID id, Chat chat, UUID snapUserId, String messageBody, Integer lifespan, LocalDateTime creationDate) {
        this.id = id;
        this.chat = chat;
        this.snapUserId =snapUserId;
        this.messageBody = messageBody;
        this.lifespan = lifespan;
        this.readTime = null;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "{ " +
                "id: " + id +
                ", message: " + messageBody +
                ", snapUserId: " + snapUserId +
                ", chatId: " + chat.getId() +
                " }";
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String message) {
        this.messageBody = message;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
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

    public void setReadTime(LocalDateTime readTime) {
        this.readTime = readTime;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
