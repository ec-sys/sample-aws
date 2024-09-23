package demo.aws.sample.kafka;

import demo.aws.sample.kafka.utils.KafkaUtil;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class KafkaStreamsApplication {

    private static final Logger logger = LoggerFactory.getLogger(KafkaStreamsApplication.class);

    static void runKafkaStreams(final KafkaStreams streams) {
        final CountDownLatch latch = new CountDownLatch(1);
        streams.setStateListener((newState, oldState) -> {
            if (oldState == KafkaStreams.State.RUNNING && newState != KafkaStreams.State.RUNNING) {
                latch.countDown();
            }
        });

        streams.start();

        try {
            latch.await();
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }

        logger.info("Streams Closed");
    }
    static Topology buildTopology(String inputTopic, String outputTopic) {
        Serde<String> stringSerde = Serdes.String();

        StreamsBuilder builder = new StreamsBuilder();

//        builder
//                .stream(inputTopic, Consumed.with(stringSerde, stringSerde))
//                .peek((k,v) -> logger.info("Observed event: key {} - value {}", k, v))
////                .filter((k,v) -> {
////                    if(v.contains("can")) return false;
////                    return true;
////                })
//                .mapValues(s -> s.toUpperCase())
////                .map((k,v) -> new KeyValue<>("newKey", v.replace("chuck", "$$$$")))
//                .peek((k,v) -> logger.info("Transformed event: key {} - value {}", k, v))
//                .to(outputTopic, Produced.with(stringSerde, stringSerde));

        // split + branch
         final Map<String, KStream<String, String>> forks =
         builder
             .stream(inputTopic, Consumed.with(stringSerde, stringSerde))
             .peek((k,v) -> logger.info("Observed event: key:{}, value: {}", k, v))
             .split(Named.as("test-"))
             .branch((key, value) -> value.contains("can"), Branched.as("can"))
             .branch((key, value) -> !value.contains("need"), Branched.as("need"))
             .defaultBranch(Branched.as("default"));
        //     // .noDefaultBranch();

             forks.get("test-can")
                 .peek((k,v) -> logger.info("Can events: key:{}, value: {}", k, v));
        //         // .to("can-topic");

             forks.get("test-need")
                 .peek((k,v) -> logger.info("Need events: key:{}, value: {}", k, v));
        //         // .to("need-topic");

             forks.get("test-default")
                 .peek((k,v) -> logger.info("Test events: key:{}, value: {}", k, v));
        //         // .to("default-topic");


        // merge
        // forks.get("test-default")
        //     .merge(forks.get("test-can"))
        //     .merge(forks.get("test-need"))
        //     .peek((k,v) -> logger.info("Merged event: key:{}, value: {}", k, v));

        return builder.build();
    }
    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            throw new IllegalArgumentException("This program takes one argument: the path to a configuration file.");
        }

        Properties props = new Properties();
        try (InputStream inputStream = new FileInputStream(args[0])) {
            props.load(inputStream);
        }

        final String inputTopic = props.getProperty("input.topic.name");
        final String outputTopic = props.getProperty("output.topic.name");

        try (KafkaUtil utility = new KafkaUtil()) {

            utility.createTopics(
                    props,
                    Arrays.asList(
                            new NewTopic(inputTopic, Optional.empty(), Optional.empty()),
                            new NewTopic(outputTopic, Optional.empty(), Optional.empty())));

            // Randomizer only used to produce sample data for this application, not typical usage
            try (KafkaUtil.Randomizer rando = utility.startNewRandomizer(props, inputTopic)) {

                KafkaStreams kafkaStreams = new KafkaStreams(
                        buildTopology(inputTopic, outputTopic),
                        props);

                Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

                logger.info("Kafka Streams 101 App Started");
                runKafkaStreams(kafkaStreams);

            }
        }
    }
}
