package fr.teama.messagepublisherservice.components;

import fr.teama.messagepublisherservice.entities.Chat;
import fr.teama.messagepublisherservice.entities.Message;
import fr.teama.messagepublisherservice.entities.PublicationType;
import fr.teama.messagepublisherservice.entities.SnapUser;
import fr.teama.messagepublisherservice.interfaces.IChatManager;
import fr.teama.messagepublisherservice.interfaces.IMessageManager;
import fr.teama.messagepublisherservice.interfaces.IPublisher;
import fr.teama.messagepublisherservice.repository.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class MessageManager implements IMessageManager {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    IChatManager chatManager;

    @Autowired
    IPublisher publisher;

    @Override
    public Message saveMessageAndNotifyRecipient(UUID chatId, UUID snapUserId, String message, Integer lifespan) throws IOException, InterruptedException {
        Message newMessage = new Message(UUID.randomUUID(), chatId, snapUserId, message, lifespan);
        Message savedMessage = messageRepository.save(newMessage);
        System.out.println("Message has been saved: " + savedMessage);
        // Publish the message on pubsub
        publisher.publish(savedMessage, PublicationType.MESSAGE);

        return savedMessage;
    }
}
