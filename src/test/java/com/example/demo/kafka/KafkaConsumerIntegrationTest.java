package com.example.demo.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "my-topic" })
class KafkaConsumerIntegrationTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    void consumerShouldReceiveMessageFromProducer() throws Exception {
        // given
        String testMessage = "Hello Embedded Kafka!";

        // when
        producer.sendMessage("my-topic", testMessage);

        // then
        // wait until message is received
        await().atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> assertThat(consumer.getMessages()).contains(testMessage));
    }

    @Test
    void testKafkaProducerAndConsumer() throws Exception {
        consumer.clearMessages();

        // send a message
        kafkaTemplate.send("my-topic", "Integration Test Message.");

        // wait a little for the consumer to process
        TimeUnit.SECONDS.sleep(2);

        List<String> messages = consumer.getMessages();
        assertThat(messages).contains("Integration Test Message.");
    }
}


