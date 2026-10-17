package fr.teama.messagepublisherservice.repository;

import fr.teama.messagepublisherservice.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatRepository extends CrudRepository<Chat, UUID> {
}
