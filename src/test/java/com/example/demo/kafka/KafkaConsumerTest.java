package com.example.demo.kafka;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

public class KafkaConsumerTest {

    @Test
    void listen_ShouldNotThrowException() {
        KafkaConsumer consumer = new KafkaConsumer();

        assertThatCode(() -> consumer.listen("Test message"))
                .doesNotThrowAnyException();
    }
}

