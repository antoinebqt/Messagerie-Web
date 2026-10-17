package fr.teama.datasaverservice;

import fr.teama.datasaverservice.entities.SnapUser;
import fr.teama.datasaverservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin
@RequestMapping(path = MyController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class MyController {
    public static final String BASE_URI = "/api";

    @Autowired
    UserRepository userRepository;

    @PostMapping("/user")
    public ResponseEntity<SnapUser> createUser(@RequestBody SnapUser user) {
        System.out.println("Request received for getting or creating user : " + user);
        SnapUser savedUser = userRepository.save(user);
        System.out.println("User saved : " + savedUser);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<SnapUser>> getUser() {
        System.out.println("Request received for getting users");
        return ResponseEntity.ok(userRepository.findAll());
    }
}
