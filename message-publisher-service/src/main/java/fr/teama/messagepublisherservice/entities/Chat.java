package fr.teama.messagepublisherservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table("chat")
public class Chat {
    @PrimaryKey
    private UUID id;

    @Column("user_id_list")
    private List<UUID> userIdList;

    @Column("chat_title")
    private String title;

    @Column("chat_creation_date_time")
    private LocalDateTime creationDate;

    public Chat() {

    }

    public Chat(UUID id, String title, List<UUID> userIdList) {
        this.id = id;
        this.title = title;
        this.userIdList = userIdList;
        this.creationDate = LocalDateTime.now();
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
