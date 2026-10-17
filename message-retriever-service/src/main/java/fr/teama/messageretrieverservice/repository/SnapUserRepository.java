package fr.teama.messageretrieverservice.repository;

import fr.teama.messageretrieverservice.entities.SnapUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SnapUserRepository extends JpaRepository<SnapUser, UUID> {
}
