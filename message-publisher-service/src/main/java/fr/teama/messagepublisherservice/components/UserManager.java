package fr.teama.messagepublisherservice.components;

import fr.teama.messagepublisherservice.entities.PublicationType;
import fr.teama.messagepublisherservice.entities.SnapUser;
import fr.teama.messagepublisherservice.interfaces.IPublisher;
import fr.teama.messagepublisherservice.interfaces.IUserManager;
import fr.teama.messagepublisherservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserManager implements IUserManager {
    @Autowired
    UserRepository userRepository;

    @Autowired
    IPublisher publisher;

    @Override
    public List<SnapUser> findAllUsers() {
        return (List<SnapUser>) userRepository.findAll();
    }

    @Override
    public SnapUser createAndSaveUser(String username) throws IOException, InterruptedException {
        SnapUser snapUser = new SnapUser(UUID.randomUUID(), username);
        userRepository.save(snapUser);
        publisher.publish(snapUser, PublicationType.USER);
        return snapUser;
    }

    @Override
    public Optional<SnapUser> findUserById(UUID id) {
        return userRepository.findById(id);
    }
}
