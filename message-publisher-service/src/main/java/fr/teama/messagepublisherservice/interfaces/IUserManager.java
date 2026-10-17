package fr.teama.messagepublisherservice.interfaces;

import fr.teama.messagepublisherservice.entities.SnapUser;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserManager {

    List<SnapUser> findAllUsers();

    SnapUser createAndSaveUser(String username) throws IOException, InterruptedException;

    Optional<SnapUser> findUserById(UUID id);
}
