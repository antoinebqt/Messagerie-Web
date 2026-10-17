package fr.teama.messagepublisherservice.repository;

import fr.teama.messagepublisherservice.entities.SnapUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<SnapUser, UUID> {

}
