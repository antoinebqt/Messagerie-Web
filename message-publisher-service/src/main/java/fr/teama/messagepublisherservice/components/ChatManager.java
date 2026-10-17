package fr.teama.messagepublisherservice.components;

import fr.teama.messagepublisherservice.entities.Chat;
import fr.teama.messagepublisherservice.entities.PublicationType;
import fr.teama.messagepublisherservice.entities.SnapUser;
import fr.teama.messagepublisherservice.interfaces.IChatManager;
import fr.teama.messagepublisherservice.interfaces.IPublisher;
import fr.teama.messagepublisherservice.interfaces.IUserManager;
import fr.teama.messagepublisherservice.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ChatManager implements IChatManager {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    IUserManager userManager;

    @Autowired
    IPublisher publisher;

    @Override
    public List<Chat> getChats() {
        return (List<Chat>) chatRepository.findAll();
    }

    @Override
    public Chat createChatBetweenTwoUsers(UUID userId1, UUID userId2) throws IOException, InterruptedException {
        List<UUID> userIdList = List.of(userId1, userId2);
        SnapUser user1 = userManager.findUserById(userId1).orElseThrow();
        SnapUser user2 = userManager.findUserById(userId2).orElseThrow();
        return saveChat(user1.getUsername() + "/" + user2.getUsername(), userIdList);
    }

    @Override
    public Chat saveChat(String title, List<UUID> userIdList) throws IOException, InterruptedException {
        Chat chat = new Chat(UUID.randomUUID(), title, userIdList);
        chatRepository.save(chat);
        publisher.publish(chat, PublicationType.CHAT);
        System.out.println("Chat has been saved : " + chat);
        return chat;
    }


}
