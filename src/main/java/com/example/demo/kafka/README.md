# Kafka with Spring Boot:
* This project demonstrates how to integrate Apache Kafka into a Spring Boot application.
* It provides a simple REST API to publish messages and a consumer to process them.
* It's covered by unit and integration tests.
* Spring Boot interacts with Kafka through the Kafka clients (KafkaTemplate for producers
* and KafkaListener for consumers).
* Zookeeper is internal Kafka coordination service.

# Features:
* Kafka producer.
* Kafka consumer.
* REST API (Kafka Controller) to publish messages.
* Unit tests for producer/consumer.
* Integration tests with Embedded Kafka.

# REST API Endpoints:
* Publish messages:
    * POST /api/v1/kafka/publish?message=HelloKafka

      Kafka Producer (KafkaProducer) will publish to topic my-topic.
      Kafka Consumer (KafkaConsumer) will listen and print the received message.

# Getting started with Kafka:
1. Use the official Confluent Kafka image:
   * docker network create kafka-net

2. Start Zookeeper
    * docker run -d --name zookeeper --network kafka-net \
-e ZOOKEEPER_CLIENT_PORT=2181 \
confluentinc/cp-zookeeper:7.5.0

3. Start Kafka broker:
    * docker run -d --name kafka --network kafka-net -p 9092:9092 \
       -e KAFKA_BROKER_ID=1 \
       -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
       -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
       -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
       confluentinc/cp-kafka:7.5.0


4. Verify it is running:
    * docker ps

5. Create Kafka topic:
    * docker exec -it kafka kafka-topics --create \
   --topic my-topic \
   --bootstrap-server localhost:9092 \
   --partitions 1 \
   --replication-factor 1
   
6. List topics:
    * docker exec -it kafka kafka-topics --list --bootstrap-server localhost:9092

7. Verify messages:
    * Open Kafka console consumer to read messages:
    * docker exec -it kafka kafka-console-consumer \
      --topic my-topic \
      --from-beginning \
      --bootstrap-server localhost:9092

8. Send a message through the API in the console consumer:
    * curl -X POST "http://localhost:8080/api/v1/kafka/publish?message=HelloKafka"





