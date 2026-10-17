package fr.teama.messageretrieverservice.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Chat {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToMany
    private List<SnapUser> userList;

    @OneToMany
    private  List<Message> messageList;

    private String title;

    private LocalDateTime creationDate;

    public Chat() {

    }

    public Chat(String title) {
        this.title = title;
        this.userList = new ArrayList<>();
        this.messageList = new ArrayList<>();
    }

    public Chat(String title, List<SnapUser> userList) {
        this.title = title;
        this.userList = userList;
        this.messageList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "{ " +
                "id: " + id +
                ", title: " + title +
                ", numberOfUsers: " + userList.size() +
                ", numberOfMessages: " + messageList.size() +
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
