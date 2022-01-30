package mk.ukim.finki.ordermanagement.kafka;

import mk.ukim.finki.sharedkernel.kafka.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public NewTopic reservationTopic() {
        return TopicBuilder.name(KafkaTopics.CHANGE_PRODUCT_SIZE_QUANTITY).build();
    }

}