package fr.teama.datasaverservice.entities;

import fr.teama.datasaverservice.components.dto.ChatDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Chat {
    @Id
    private UUID id;

    @ManyToMany
    private List<SnapUser> userList;

    @OneToMany(fetch = FetchType.EAGER)
    private  List<Message> messageList;

    private String title;

    private LocalDateTime creationDate;

    public Chat() {

    }

    public Chat(UUID id, String title, List<SnapUser> userList, LocalDateTime creationDate) {
        this.id = id;
        this.userList = userList;
        this.title = title;
        this.messageList = new ArrayList<>();
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "{ " +
                "id: " + id +
                ", title: " + title +
                ", creationDate: " + creationDate +
                " }";
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public List<SnapUser> getUserList() {
        return userList;
    }

    public void setUserList(List<SnapUser> userList) {
        this.userList = userList;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
