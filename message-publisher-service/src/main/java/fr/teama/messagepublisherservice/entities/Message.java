package fr.teama.messagepublisherservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("message")
public class Message {
    @PrimaryKey
    private UUID id;

    @Column("chat_id")
    private UUID chatId;

    @Column("snap_user_id")
    private UUID snapUserId;

    @Column("text")
    private String text;

    @Column("lifespan")
    private Integer lifespan;

    @Column("read_time")
    private LocalDateTime readTime;

    @Column("message_creation_date_time")
    private LocalDateTime creationDate;

    public Message() {

    }

    public Message(UUID id, UUID chatId, UUID snapUserId, String messageBody, Integer lifespan) {
        this.id = id;
        this.chatId = chatId;
        this.snapUserId = snapUserId;
        this.text = messageBody;
        this.lifespan = lifespan;
        this.readTime = null;
        creationDate = LocalDateTime.now();
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public UUID getSnapUserId() {
        return snapUserId;
    }

    public void setSnapUserId(UUID snapUserId) {
        this.snapUserId = snapUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
