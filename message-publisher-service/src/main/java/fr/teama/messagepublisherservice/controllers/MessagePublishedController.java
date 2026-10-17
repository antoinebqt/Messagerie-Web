package fr.teama.messagepublisherservice.controllers;

import fr.teama.messagepublisherservice.controllers.dto.ChatDTO;
import fr.teama.messagepublisherservice.controllers.dto.MessageDTO;
import fr.teama.messagepublisherservice.entities.Chat;
import fr.teama.messagepublisherservice.entities.Message;
import fr.teama.messagepublisherservice.entities.SnapUser;
import fr.teama.messagepublisherservice.exceptions.UserAlreadyExistException;
import fr.teama.messagepublisherservice.interfaces.IChatManager;
import fr.teama.messagepublisherservice.interfaces.IDataManager;
import fr.teama.messagepublisherservice.interfaces.IMessageManager;
import fr.teama.messagepublisherservice.interfaces.IUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin
@RequestMapping(path = MessagePublishedController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class MessagePublishedController {
    public static final String BASE_URI = "/api/message-published";

    @Autowired
    private IMessageManager messageManager;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IChatManager chatManager;

    @Autowired
    IDataManager dataManager;

    @PostMapping(value = "/send-message")
    public ResponseEntity<Message> sendMessage(@RequestBody MessageDTO messageDTO) throws IOException, InterruptedException {
        System.out.println("Request received to send a message");
        if (messageDTO == null)
            return ResponseEntity.status(500).body(null);

        Message message = messageManager.saveMessageAndNotifyRecipient(messageDTO.getChatId(), messageDTO.getUserId(), messageDTO.getMessage(), messageDTO.getLifespan());
        return ResponseEntity.ok(message);
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<SnapUser>> getAllUser() {
        System.out.println("Request received for getting all users");
        List<SnapUser> userList = userManager.findAllUsers();
        return ResponseEntity.ok(userList);
    }

    @PostMapping("/create-user")
    public ResponseEntity<SnapUser> createUser(@RequestBody String username) throws IOException, InterruptedException, UserAlreadyExistException {
        System.out.println("Request received for creating user : " + username);
        SnapUser user = userManager.createAndSaveUser(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/chats")
    public ResponseEntity<List<Chat>> getChats() {
        System.out.println("Request received for getting all chats");
        List<Chat> chats = chatManager.getChats();
        return ResponseEntity.ok(chats);
    }

    @PostMapping("/create-chat")
    public ResponseEntity<Chat> createChatBetweenTwoUser(@RequestBody ChatDTO chatDTO) throws IOException, InterruptedException {
        System.out.println("Request received for creating the chat between 2 users");
        Optional<SnapUser> user1 = userManager.findUserById(chatDTO.getUserId1());
        Optional<SnapUser> user2 = userManager.findUserById(chatDTO.getUserId2());

        if (user1.isEmpty() || user2.isEmpty())
            return ResponseEntity.status(500).body(null);

        Chat chat = chatManager.createChatBetweenTwoUsers(user1.get().getId(), user2.get().getId());
        return ResponseEntity.ok(chat);
    }

    @PostMapping("/reset-db")
    public ResponseEntity<String> resetDB() {
        System.out.println("Request received to reset databases");
        dataManager.resetDB();
        return ResponseEntity.ok("Databases reset");
    }
}
