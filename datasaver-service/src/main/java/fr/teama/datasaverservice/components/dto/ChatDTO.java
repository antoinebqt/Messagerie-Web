package fr.teama.datasaverservice.components.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatDTO {
    private UUID id;
    private List<UUID> userIdList;
    private String title;
    private LocalDateTime creationDate;

    public ChatDTO() {

    }

    @Override
    public String toString() {
        return "{ " +
                "id: " + id +
                ", title: " + title +
                ", numberOfUsers: " + userIdList.size() +
                ", creationDate: " + creationDate +
                " }";
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<UUID> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<UUID> userIdList) {
        this.userIdList = userIdList;
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
