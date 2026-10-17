package fr.teama.messagepublisherservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.teama.messagepublisherservice.configuration.JacksonConfig;
import jakarta.persistence.GeneratedValue;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table("snap_user")
public class SnapUser {
    @PrimaryKey
    private UUID id;

    @Column("username")
    private String username;

    @Column("user_creation_date_time")
    private LocalDateTime creationDate;

    public SnapUser() {

    }

    public SnapUser(UUID id, String username) {
        this.id = id;
        this.username = username;
        this.creationDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "{ " +
                "id: " + id +
                ", username: " + username +
                ", creationDate: " + creationDate +
                " }";
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
