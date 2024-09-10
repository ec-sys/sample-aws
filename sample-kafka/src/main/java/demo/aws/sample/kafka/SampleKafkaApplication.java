package demo.aws.sample.kafka;

import demo.aws.sample.common_util.util.SequenceGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SampleKafkaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SampleKafkaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        SequenceGenerator generator = new SequenceGenerator(1);
        log.info("snowflake id: {}", generator.nextId());
    }
}
