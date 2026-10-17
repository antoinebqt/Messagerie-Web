package fr.teama.messagepublisherservice.interfaces;

import fr.teama.messagepublisherservice.entities.Chat;
import fr.teama.messagepublisherservice.entities.Message;
import fr.teama.messagepublisherservice.entities.SnapUser;

import java.io.IOException;
import java.util.UUID;

public interface IMessageManager {

    Message saveMessageAndNotifyRecipient(UUID chatId, UUID snapUserId, String message, Integer lifespan) throws IOException, InterruptedException;
}
