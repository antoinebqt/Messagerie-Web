package fr.teama.datasaverservice.interfaces;

import fr.teama.datasaverservice.entities.SnapUser;

public interface IDataSaver {

    void saveChat(String stringUtf8);

    void saveUser(String stringUtf8);

    void saveMessage(String stringUtf8);
}
