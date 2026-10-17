package fr.teama.messageretrieverservice.components;

import fr.teama.messageretrieverservice.interfaces.IDataManager;
import fr.teama.messageretrieverservice.repository.ChatRepository;
import fr.teama.messageretrieverservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataManager implements IDataManager {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void resetDB() {
        chatRepository.deleteAll();
        userRepository.deleteAll();
    }
}
