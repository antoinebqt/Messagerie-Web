package fr.teama.messagepublisherservice.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import fr.teama.messagepublisherservice.entities.PublicationType;
import fr.teama.messagepublisherservice.interfaces.IPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class Publisher implements IPublisher {
    @Autowired
    ObjectMapper objectMapper;

    @Value("${gcloud.pubsub.projectId}")
    String projectId;

    @Value("${gcloud.pubsub.chats.topicId}")
    String chatTopicId;

    @Value("${gcloud.pubsub.users.topicId}")
    String userTopicId;

    @Value("${gcloud.pubsub.messages.topicId}")
    String messageTopicId;

    @Value("${gcloud.credentials}")
    String credentialsJson;

    @Override
    public <T> void publish(T object, PublicationType type) throws IOException, InterruptedException {

        String topicId = switch (type) {
            case USER -> userTopicId;
            case CHAT -> chatTopicId;
            case MESSAGE -> messageTopicId;
        };

        System.out.println("Publishing object: \"" + object + "\" on topic " + topicId + " in project " + projectId);

        ProjectTopicName topicName = ProjectTopicName.of(projectId, topicId);
        com.google.cloud.pubsub.v1.Publisher publisher = null;

        GoogleCredentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(credentialsJson.getBytes()));

        try {
            // Create a publisher instance with default settings bound to the topic
            publisher = com.google.cloud.pubsub.v1.Publisher.newBuilder(topicName)
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();

            String dataJson = objectMapper.writeValueAsString(object);

            System.out.println("Data to publish: " + dataJson);

            ByteString data = ByteString.copyFromUtf8(dataJson);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

            // Once published, returns a server-assigned message id (unique within the topic)
            ApiFuture<String> future = publisher.publish(pubsubMessage);

            // Add an asynchronous callback to handle success / failure
            ApiFutures.addCallback(
                    future,
                    new ApiFutureCallback<>() {

                        @Override
                        public void onFailure(Throwable throwable) {
                            if (throwable instanceof ApiException apiException) {
                                // details on the API exception
                                System.out.println(apiException.getStatusCode().getCode());
                                System.out.println(apiException.isRetryable());
                            }
                            System.out.println("Error publishing object : " + object + " on topic " + topicId + " in project " + projectId);
                        }

                        @Override
                        public void onSuccess(String publishedObjectId) {
                            // Once published, returns server-assigned message ids (unique within the topic)
                            System.out.println("Published object ID: " + publishedObjectId);
                        }
                    },
                    MoreExecutors.directExecutor());
        } finally {
            if (publisher != null) {
                // When finished with the publisher, shutdown to free up resources.
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
    }
}
