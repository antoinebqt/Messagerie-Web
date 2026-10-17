package fr.teama.messagepublisherservice.controllers.dto;

import java.util.UUID;

public class ChatDTO {
    private UUID userId1;
    private UUID userId2;

    public UUID getUserId1() {
        return userId1;
    }

    public void setUserId1(UUID userId1) {
        this.userId1 = userId1;
    }

    public UUID getUserId2() {
        return userId2;
    }

    public void setUserId2(UUID userId2) {
        this.userId2 = userId2;
    }
}
