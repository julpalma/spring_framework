package com.example.demo.api;

import com.example.demo.kafka.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KafkaControllerTest {

        @Mock
        private KafkaProducer producer;

        @InjectMocks
        private KafkaController controller;

        @Test
        void sendMessage_ShouldReturnConfirmationAndCallProducer() {
            // given
            String testMessage = "Hello Kafka!";

            // when
            String result = controller.sendMessage(testMessage);

            // then
            assertThat(result).isEqualTo("Message sent!");
            verify(producer).sendMessage("my-topic", testMessage); // verify producer was called
        }



}
