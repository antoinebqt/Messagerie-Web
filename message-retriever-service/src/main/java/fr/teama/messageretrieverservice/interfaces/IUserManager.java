package fr.teama.messageretrieverservice.interfaces;

import fr.teama.messageretrieverservice.entities.SnapUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserManager {

    List<SnapUser> findAllUsers();

    Optional<SnapUser> findUserByUsername(String username);

    Optional<SnapUser> findUserById(UUID id);
}
