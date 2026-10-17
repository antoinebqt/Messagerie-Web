package fr.teama.datasaverservice.interfaces;

import java.io.IOException;

public interface ISubscriber {

    void subscribeToTopic(String projectId, String subscriptionId, String type) throws IOException, InterruptedException;
}
