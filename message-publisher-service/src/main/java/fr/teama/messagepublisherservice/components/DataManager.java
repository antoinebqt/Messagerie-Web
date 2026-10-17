package fr.teama.messagepublisherservice.components;

import fr.teama.messagepublisherservice.interfaces.IDataManager;
import fr.teama.messagepublisherservice.repository.ChatRepository;
import fr.teama.messagepublisherservice.repository.MessageRepository;
import fr.teama.messagepublisherservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataManager implements IDataManager {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void resetDB() {
        messageRepository.deleteAll();
        chatRepository.deleteAll();
        userRepository.deleteAll();
    }
}
