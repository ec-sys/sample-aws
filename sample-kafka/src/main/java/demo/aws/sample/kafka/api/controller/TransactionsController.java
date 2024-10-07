package demo.aws.sample.kafka.api.controller;

import demo.aws.sample.common_util.model.Order;
import demo.aws.sample.kafka.api.request.InputParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/kafka")
public class TransactionsController {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionsController.class);

    long id = 1;
    long groupId = 1;

    @Autowired
    KafkaTemplate<String, Order> kafkaTemplate;

    @PostMapping("/transactions")
    public void generateAndSendMessagesSync(@RequestBody InputParameters inputParameters) {
        for (long i = 0; i < inputParameters.getNumberOfMessages(); i++) {
            Order o = new Order(String.valueOf(id++), i + 1, i + 2, 1000, "NEW", groupId);
            CompletableFuture<SendResult<String, Order>> result =
                    kafkaTemplate.send("transactions", o.getId(), o);
            result.whenComplete((sr, ex) ->
                    LOG.info("Sent({}): {}", sr.getProducerRecord().key(), sr.getProducerRecord().value()));
        }
        groupId++;
    }

    @PostMapping("/transactions-async")
    public void generateAndSendMessagesAsync(@RequestBody InputParameters inputParameters) {
        for (long i = 0; i < inputParameters.getNumberOfMessages(); i++) {
            Order o = new Order(String.valueOf(id++), i + 1, i + 2, 1000, "NEW", groupId);
            CompletableFuture<SendResult<String, Order>> result =
                    kafkaTemplate.send("transactions-async", o.getId(), o);
            result.whenComplete((sr, ex) ->
                    LOG.info("Sent({}): {}", sr.getProducerRecord().key(), sr.getProducerRecord().value()));
        }
        groupId++;
    }
}
