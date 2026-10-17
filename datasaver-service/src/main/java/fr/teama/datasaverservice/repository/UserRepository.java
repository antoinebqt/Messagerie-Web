package fr.teama.datasaverservice.repository;

import fr.teama.datasaverservice.entities.SnapUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<SnapUser, UUID> {
}
