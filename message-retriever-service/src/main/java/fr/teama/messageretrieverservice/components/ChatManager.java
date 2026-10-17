package fr.teama.messageretrieverservice.components;

import fr.teama.messageretrieverservice.controllers.dto.ChatUserDTO;
import fr.teama.messageretrieverservice.entities.Chat;
import fr.teama.messageretrieverservice.entities.Message;
import fr.teama.messageretrieverservice.entities.SnapUser;
import fr.teama.messageretrieverservice.interfaces.IChatManager;
import fr.teama.messageretrieverservice.repository.ChatRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ChatManager implements IChatManager {

    @Autowired
    ChatRepository chatRepository;

    @Override
    public Optional<List<Message>> fetchChatById(ChatUserDTO chatUserDTO) {
        Optional<Chat> optionalChat = chatRepository.findById(chatUserDTO.getChatId());

        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            List<Message> messages = chat.getMessageList();
            Optional<List<Message>> messagesToReturn = chatUserDTO.getLastMessageCreationDate() == null ?
                    Optional.of(messages)
                    : Optional.of(
                    messages.stream()
                            .filter(message -> (message.getCreationDate().isAfter(chatUserDTO.getLastMessageCreationDate())))
                            .collect(Collectors.toList()));

            System.out.println("Messages in chat: " + messages);
            messageProcessing(messages, chatUserDTO.getUserId());
            chat.setMessageList(messages);

            chatRepository.save(chat);

            return messagesToReturn;
        } else {
            throw new EntityNotFoundException("Chat with id " + chatUserDTO.getChatId() + " not found");
        }
    }

    @Scheduled(cron = "59 59 23 * * SUN")
    public void clearShortLivingMessages() {
        List<Chat> chats = chatRepository.findAll();
        for (Chat chat : chats) {
            List<Message> messageToRemove = new ArrayList<>();
            List<Message> messages = chat.getMessageList();
            for (Message message : messages) {
                if(message.getReadTime() == null
                    && message.getLifespan() != -1
                    && message.getCreationDate().plusSeconds(518400).isBefore(LocalDateTime.now())) {
                    messageToRemove.add(message);
                }
            }
            messages.removeAll(messageToRemove);
            chat.setMessageList(messages);
            chatRepository.save(chat);
        }
    }

    private void messageProcessing(List<Message> messages, UUID userId) {
        List<Message> messagesToRemove = new ArrayList<>();
        for (Message message : messages) {
            UUID senderId = message.getSnapUserId();
            LocalDateTime readTime = message.getReadTime();
            if (Objects.equals(userId, senderId)) {
                if (readTime != null && message.getLifespan() != -1) {
                    if (readTime.plusSeconds(message.getLifespan()).isBefore(LocalDateTime.now())) {
                        messagesToRemove.add(message);
                    }
                }
            } else {
                if (readTime != null && message.getLifespan() != -1) {
                    if (readTime.plusSeconds(message.getLifespan()).isBefore(LocalDateTime.now())) {
                        messagesToRemove.add(message);
                    }
                } else if (readTime == null) {
                    message.setReadTime(LocalDateTime.now());
                }
            }
        }
        messages.removeAll(messagesToRemove);
    }

    @Override
    public List<Chat> getChats() {
        return chatRepository.findAll();
    }

    @Override
    public Chat fetchChatBetweenTwoUsers(SnapUser user1, SnapUser user2) {
        List<SnapUser> userList = List.of(user1, user2);

        for (Chat chat : user1.getChatList()) {
            if (chat.getUserList().size() == 2 && new HashSet<>(chat.getUserList()).containsAll(userList)) {
                return chat;
            }
        }
        return null;
    }
}
