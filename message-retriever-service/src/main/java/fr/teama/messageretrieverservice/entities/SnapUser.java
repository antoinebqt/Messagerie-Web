package fr.teama.messageretrieverservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class SnapUser {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToMany
    @JsonIgnore
    private List<Chat> chatList;

    @Column(unique=true)
    private String username;

    private LocalDateTime creationDate;

    public SnapUser() {

    }

    public SnapUser(String username) {
        this.username = username;
        this.chatList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "{ " +
                "id: " + id +
                ", username: " + username +
                "creationDate: " + creationDate +
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
