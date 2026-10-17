package fr.teama.datasaverservice.configuration;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import fr.teama.datasaverservice.components.DataSaver;
import fr.teama.datasaverservice.components.dto.ChatDTO;
import fr.teama.datasaverservice.components.dto.SnapUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;

@Configuration
public class PubSubReceiver {
    // Create a message channel for messages arriving from the subscription `sub-one`.
    @Autowired
    DataSaver dataSaver;

    @Value("${gcloud.pubsub.chatSubscriptionId}")
    String chatSubscriptionId;

    @Value("${gcloud.pubsub.userSubscriptionId}")
    String userSubscriptionId;

    @Value("${gcloud.pubsub.messageSubscriptionId}")
    String messageSubscriptionId;

    @Bean
    public MessageChannel inputChatChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel inputUserChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel inputMessageChannel() {
        return new PublishSubscribeChannel();
    }

    // Create an inbound channel adapter to listen to the subscription `sub-one` and send
    // messages to the input message channel.
    @Bean
    public PubSubInboundChannelAdapter chatChannelAdapter(
            @Qualifier("inputChatChannel") MessageChannel messageChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, chatSubscriptionId);
        adapter.setOutputChannel(messageChannel);
        adapter.setAckMode(AckMode.MANUAL);
        adapter.setPayloadType(String.class);
        return adapter;
    }

    @Bean
    public PubSubInboundChannelAdapter userChannelAdapter(
            @Qualifier("inputUserChannel") MessageChannel messageChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, userSubscriptionId);
        adapter.setOutputChannel(messageChannel);
        adapter.setAckMode(AckMode.MANUAL);
        adapter.setPayloadType(String.class);
        return adapter;
    }

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("inputMessageChannel") MessageChannel messageChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, messageSubscriptionId);
        adapter.setOutputChannel(messageChannel);
        adapter.setAckMode(AckMode.MANUAL);
        adapter.setPayloadType(String.class);
        return adapter;
    }

    // Define what happens to the messages arriving in the message channel.
    @ServiceActivator(inputChannel = "inputChatChannel")
    public void chatReceiver(
            String payload,
            @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        System.out.println("CHAT arrived via an inbound channel adapter from sub-one! Payload: " + payload);
        dataSaver.saveChat(payload);
        message.ack();
    }

    // Define what happens to the messages arriving in the message channel.
    @ServiceActivator(inputChannel = "inputUserChannel")
    public void userReceiver(
            String payload,
            @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        System.out.println("USER arrived via an inbound channel adapter from sub-one! Payload: " + payload);
        dataSaver.saveUser(payload);
        message.ack();
    }

    // Define what happens to the messages arriving in the message channel.
    @ServiceActivator(inputChannel = "inputMessageChannel")
    public void messageReceiver(
            String payload,
            @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        System.out.println("MESSAGE arrived via an inbound channel adapter from sub-one! Payload: " + payload);
        dataSaver.saveMessage(payload);
        message.ack();
    }
}
