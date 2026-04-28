package ai.johnsoneyo.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;


@SpringBootApplication(exclude = {KafkaAutoConfiguration.class})
public class EventOutcomesProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventOutcomesProcessorApplication.class, args);
    }
}