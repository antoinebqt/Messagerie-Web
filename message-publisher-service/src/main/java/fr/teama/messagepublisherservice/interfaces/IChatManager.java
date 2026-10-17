package fr.teama.messagepublisherservice.interfaces;

import fr.teama.messagepublisherservice.entities.Chat;
import fr.teama.messagepublisherservice.entities.SnapUser;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IChatManager {

    List<Chat> getChats();

    Chat saveChat(String title, List<UUID> userIdList) throws IOException, InterruptedException;

    Chat createChatBetweenTwoUsers(UUID userId1, UUID userId2) throws IOException, InterruptedException;
}
