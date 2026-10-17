package fr.teama.messageretrieverservice.interfaces;


import fr.teama.messageretrieverservice.controllers.dto.ChatUserDTO;
import fr.teama.messageretrieverservice.entities.Chat;
import fr.teama.messageretrieverservice.entities.Message;
import fr.teama.messageretrieverservice.entities.SnapUser;

import java.util.List;
import java.util.Optional;

public interface IChatManager {

    Optional<List<Message>> fetchChatById(ChatUserDTO chatUserDTO);

    List<Chat> getChats();

    Chat fetchChatBetweenTwoUsers(SnapUser snapUser, SnapUser snapUser1);
}
