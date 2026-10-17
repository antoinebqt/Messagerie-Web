package fr.teama.messageretrieverservice.components;


import fr.teama.messageretrieverservice.entities.SnapUser;
import fr.teama.messageretrieverservice.interfaces.IUserManager;
import fr.teama.messageretrieverservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserManager implements IUserManager {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<SnapUser> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<SnapUser> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<SnapUser> findUserById(UUID id) {
        return userRepository.findById(id);
    }
}
