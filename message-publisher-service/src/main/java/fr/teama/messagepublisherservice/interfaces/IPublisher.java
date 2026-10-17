package fr.teama.messagepublisherservice.interfaces;

import fr.teama.messagepublisherservice.entities.PublicationType;

import java.io.IOException;

public interface IPublisher {
    <T> void publish(T object, PublicationType type) throws IOException, InterruptedException;
}
