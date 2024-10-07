package demo.aws.sample.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class SimpleConsumerWithBootStrapServers {

    private static final String CONSUMER_GROUP_ID = "trans-group-id";
    private static final String OUTPUT_TOPIC = "trans-output";
    private static final String INPUT_TOPIC = "trans-input";

    public static void main(String[] args) {
        try(final Consumer<String, String> consumer = createConsumer()) {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMinutes(1));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("received value : " + record.value());
                }
            }
        }
    }

    private static Consumer<String, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // Create the consumer using props.
        final Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        // Subscribe to the topic.
        consumer.subscribe(Arrays.asList(INPUT_TOPIC));
        return consumer;
    }

}