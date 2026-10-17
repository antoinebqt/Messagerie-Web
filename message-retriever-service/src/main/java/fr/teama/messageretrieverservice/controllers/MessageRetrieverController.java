package fr.teama.messageretrieverservice.controllers;

import fr.teama.messageretrieverservice.controllers.dto.ChatDTO;
import fr.teama.messageretrieverservice.controllers.dto.ChatUserDTO;
import fr.teama.messageretrieverservice.entities.Chat;
import fr.teama.messageretrieverservice.entities.Message;
import fr.teama.messageretrieverservice.entities.SnapUser;
import fr.teama.messageretrieverservice.interfaces.IChatManager;
import fr.teama.messageretrieverservice.repository.SnapUserRepository;
import fr.teama.messageretrieverservice.interfaces.IDataManager;
import fr.teama.messageretrieverservice.interfaces.IUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin
@RequestMapping(path = MessageRetrieverController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class MessageRetrieverController {
    public static final String BASE_URI = "/api/message-retrieved";

    @Autowired
    IUserManager userManager;

    @Autowired
    IDataManager dataManager;

    @Autowired
    IChatManager chatManager;

    @PostMapping("/update-chat")
    public ResponseEntity<List<Message>> getUpdatedChat(@RequestBody ChatUserDTO chatUserDTO) {
        System.out.println("Request received for getting the new version of a specific chat");
        Optional<List<Message>> messages = chatManager.fetchChatById(chatUserDTO);
        return messages.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(500).body(null));
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<SnapUser>> getAllUser() {
        System.out.println("Request received for getting all users");
        List<SnapUser> userList = userManager.findAllUsers();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/chats")
    public ResponseEntity<List<Chat>> getChats() {
        System.out.println("Request received for getting all chats");
        List<Chat> chats = chatManager.getChats();
        return ResponseEntity.ok(chats);
    }

    @PostMapping("/reset-db")
    public ResponseEntity<String> resetDB() {
        System.out.println("Request received to reset databases");
        dataManager.resetDB();
        return ResponseEntity.ok("Databases reset");
    }

    @PostMapping("/login-user")
    public ResponseEntity<SnapUser> loginUser(@RequestBody String username) {
        System.out.println("Request received to login user " + username);
        Optional<SnapUser> user = userManager.findUserByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(500).body(null));
    }

    @PostMapping("/user-exists")
    public boolean userExists(@RequestBody String username) {
        System.out.println("Request received to check if user " + username + " exists");
        Optional<SnapUser> user = userManager.findUserByUsername(username);
        return user.isPresent();
    }

    @PostMapping("/get-chat")
    public ResponseEntity<Chat> getChatBetweenTwoUser(@RequestBody ChatDTO chatDTO) {
        System.out.println("Request received for getting the chat between 2 users");
        Optional<SnapUser> user1 = userManager.findUserById(chatDTO.getUserId1());
        Optional<SnapUser> user2 = userManager.findUserById(chatDTO.getUserId2());

        if (user1.isEmpty() || user2.isEmpty())
            return ResponseEntity.status(500).body(null);

        Chat chat = chatManager.fetchChatBetweenTwoUsers(user1.get(), user2.get());
        if (chat == null)
            return ResponseEntity.status(500).body(null);
        return ResponseEntity.ok(chat);
    }

}
