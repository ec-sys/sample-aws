package demo.aws.sample.kafka.service;

import demo.aws.sample.common_util.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class Listener {

    private static final Logger LOG = LoggerFactory.getLogger(Listener.class);

    @KafkaListener(
            id = "transactions-async",
            topics = "transactions-async",
            groupId = "demo-async"
    )
    public void listen(@Payload Order order,
                       @Header(KafkaHeaders.OFFSET) Long offset,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) throws InterruptedException {
        LOG.info("[partition={},offset={}] Starting: {}", partition, offset, order);
        Thread.sleep(5000L);
        LOG.info("[partition={},offset={}] Finished: {}", partition, offset, order);
    }

}