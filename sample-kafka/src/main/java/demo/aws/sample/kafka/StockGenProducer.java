package demo.aws.sample.kafka;

import demo.aws.sample.kafka.domain.constant.StockConstant;
import demo.aws.sample.kafka.domain.model.trade.Trade;
import demo.aws.sample.kafka.domain.serde.JsonSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;


public class StockGenProducer {

    public static KafkaProducer<String, Trade> producer = null;

    public static void main(String[] args) throws Exception {

        System.out.println("Press CTRL-C to stop generating data");

        // add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Shutting Down");
                if (producer != null)
                    producer.close();
            }
        });

        JsonSerializer<Trade> tradeSerializer = new JsonSerializer<>();

        // Configuring producer
        Properties props;
        if (args.length==1)
            props = LoadConfigs.loadConfig(args[0]);
        else
            props = LoadConfigs.loadConfig();

        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, tradeSerializer.getClass().getName());

        // Starting producer
        producer = new KafkaProducer<>(props);


        // initialize

        Random random = new Random();
        long iter = 0;

        Map<String, Integer> prices = new HashMap<>();

        for (String ticker : StockConstant.TICKERS)
            prices.put(ticker, StockConstant.START_PRICE);

        // Start generating events, stop when CTRL-C

        while (true) {
            iter++;
            for (String ticker : StockConstant.TICKERS) {
                double log = random.nextGaussian() * 0.25 + 1; // random var from lognormal dist with stddev = 0.25 and mean=1
                int size = random.nextInt(100);
                int price = prices.get(ticker);

                // flunctuate price sometimes
                if (iter % 10 == 0) {
                    price = price + random.nextInt(StockConstant.MAX_PRICE_CHANGE * 2) - StockConstant.MAX_PRICE_CHANGE;
                    prices.put(ticker, price);
                }

                Trade trade = new Trade("ASK",ticker,(price+log),size);
                // Note that we are using ticker as the key - so all asks for same stock will be in same partition
                ProducerRecord<String, Trade> record = new ProducerRecord<>(StockConstant.STOCK_TOPIC, ticker, trade);

                producer.send(record, (RecordMetadata r, Exception e) -> {
                    if (e != null) {
                        System.out.println("Error producing events");
                        e.printStackTrace();
                    }
                });

                // Sleep a bit, otherwise it is frying my machine
                Thread.sleep(StockConstant.DELAY);
            }
        }
    }
}