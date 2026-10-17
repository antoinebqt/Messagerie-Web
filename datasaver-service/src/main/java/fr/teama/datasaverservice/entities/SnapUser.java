package fr.teama.datasaverservice.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class SnapUser {
    @Id
    private UUID id;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Chat> chatList;

    @Column(unique=true)
    private String username;

    private LocalDateTime creationDate;

    public SnapUser() {

    }

    public SnapUser(UUID id, String username, LocalDateTime creationDate) {
        this.id = id;
        this.username = username;
        this.chatList = new ArrayList<>();
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "{ " +
                "id: " + id +
                ", username: " + username +
                ", creationDate: " + creationDate +
                " }";
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
