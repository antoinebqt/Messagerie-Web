package fr.teama.datasaverservice.components.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class SnapUserDTO {
    private UUID id;
    private String username;
    private LocalDateTime creationDate;

    public SnapUserDTO() {

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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
