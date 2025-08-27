package com.example.demo.api;

import com.example.demo.kafka.KafkaProducer;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.verify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class KafkaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KafkaProducer producer;    //Spring will inject this mock into KafkaController

    @Test
    void sendMessageEndpoint_ShouldReturnConfirmationAndCallProducer() throws Exception {
        // given
        String testMessage = "Hello Kafka!";

        // when & then
        mockMvc.perform(post("/api/v1/kafka/publish")
                        .param("message", testMessage))
                .andExpect(status().isOk())
                .andExpect(content().string("Message sent!"));

        // verify producer was called
        verify(producer).sendMessage("my-topic", testMessage);
    }
}
