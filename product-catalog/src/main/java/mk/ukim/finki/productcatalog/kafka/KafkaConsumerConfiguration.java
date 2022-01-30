package mk.ukim.finki.productcatalog.kafka;

import mk.ukim.finki.sharedkernel.domain.dto.request.ProductSizeDto;
import mk.ukim.finki.sharedkernel.kafka.KafkaGroups;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaGroups.CLOTHING_GROUP);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    @Bean
    public ConsumerFactory<String, ProductSizeDto> reduceProductSizeQuantityConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(ProductSizeDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProductSizeDto> reduceProductSizeQuantityContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProductSizeDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(reduceProductSizeQuantityConsumerFactory());
        return factory;
    }

}