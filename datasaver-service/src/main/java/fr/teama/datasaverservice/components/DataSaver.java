package fr.teama.datasaverservice.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.teama.datasaverservice.components.dto.ChatDTO;
import fr.teama.datasaverservice.components.dto.MessageDTO;
import fr.teama.datasaverservice.components.dto.SnapUserDTO;
import fr.teama.datasaverservice.entities.Chat;
import fr.teama.datasaverservice.entities.Message;
import fr.teama.datasaverservice.entities.SnapUser;
import fr.teama.datasaverservice.interfaces.IDataSaver;
import fr.teama.datasaverservice.repository.ChatRepository;
import fr.teama.datasaverservice.repository.MessageRepository;
import fr.teama.datasaverservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DataSaver implements IDataSaver {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void saveChat(String stringUtf8) {
        try {
            System.out.println("Try saving chat : " + stringUtf8);
            ChatDTO chatDTO = objectMapper.readValue(stringUtf8, ChatDTO.class);
            List<SnapUser> snapUserList = userRepository.findAllById(chatDTO.getUserIdList());
            Chat chat = new Chat(chatDTO.getId(), chatDTO.getTitle(), snapUserList, chatDTO.getCreationDate());
            Chat savedChat = chatRepository.save(chat);
            System.out.println("Chat has been saved : " + savedChat);

            // Update user chat list
            for (SnapUser snapUser : snapUserList) {
                if (snapUser.getChatList() == null) {
                    snapUser.setChatList(new ArrayList<>());
                }
                snapUser.getChatList().add(chat);
                userRepository.save(snapUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String stringUtf8) {
        try {
            System.out.println("Try saving user : " + stringUtf8);
            SnapUserDTO userDTO = objectMapper.readValue(stringUtf8, SnapUserDTO.class);
            SnapUser user = new SnapUser(userDTO.getId(), userDTO.getUsername(), userDTO.getCreationDate());
            SnapUser savedUser = userRepository.save(user);
            System.out.println("User has been saved : " + savedUser);
        } catch (Exception e) {
            System.out.println("Error saving user : " + e.getMessage());
            System.out.println(e.toString());
        }
    }

    @Override
    public void saveMessage(String stringUtf8) {
        try {
            System.out.println("Try saving message : " + stringUtf8);
            MessageDTO messageDTO = objectMapper.readValue(stringUtf8, MessageDTO.class);
            Optional<Chat> chat = chatRepository.findById(messageDTO.getChatId());
            if (chat.isEmpty()) {
                System.out.println("Chat not found, message not saved : " + messageDTO);
                return;
            }
            Message message = new Message(messageDTO.getId(), chat.get(), messageDTO.getSnapUserId(), messageDTO.getText(), messageDTO.getLifespan(), messageDTO.getCreationDate());
            Message savedMessage = messageRepository.save(message);
            System.out.println("Message has been saved : " + savedMessage);

            // Update chat message list
            if (chat.get().getMessageList() == null)
                chat.get().setMessageList(new ArrayList<>());
            chat.get().getMessageList().add(message);
            chatRepository.save(chat.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
