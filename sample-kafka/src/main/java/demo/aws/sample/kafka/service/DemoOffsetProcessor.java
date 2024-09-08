package demo.aws.sample.kafka.service;

import demo.aws.sample.common_util.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class DemoOffsetProcessor {
    private static final Logger LOG = LoggerFactory
            .getLogger(DemoOffsetProcessor.class);

    public void process(Order order, Acknowledgment acknowledgment) {
        LOG.info("Processing: {}", order.getId());
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LOG.info("Finished: {}", order.getId());
        acknowledgment.acknowledge();
    }
}
