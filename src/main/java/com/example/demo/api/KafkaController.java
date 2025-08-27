package com.example.demo.api;

import com.example.demo.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

//API Layer
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/kafka")
public class KafkaController {

    private final KafkaProducer producer;

    public KafkaController(KafkaProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/publish")
    public String sendMessage(@RequestParam String message) {
        producer.sendMessage("my-topic", message);
        return "Message sent!";
    }

}
