package com.danilodps.kafkaconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class DpsKafkaConsumerProducerApplication {

	static void main(String[] args) {
		SpringApplication.run(DpsKafkaConsumerProducerApplication.class, args);
	}
}
