package com.example.demo.kafka;

import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Service
public class KafkaConsumer {

    private final List<String> messages = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void listen(String message) {
        System.out.println("Received: " + message);
        messages.add(message);
    }

    public void clearMessages() {
        messages.clear();
    }

}

