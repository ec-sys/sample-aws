package demo.aws.sample.rabbitmq.api.controller;

import demo.aws.sample.rabbitmq.api.request.RabbitMQPublishRequest;
import demo.aws.sample.rabbitmq.domain.constant.RabbitMQConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbitmq")
@Slf4j
public class RabbitMQProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/topic-exchange/publish")
    public ResponseEntity<String> publishTopicExchangeMessage(@RequestBody RabbitMQPublishRequest request) {
        String response = "Done sending topic-exchange message: " + request.getMessage();
        rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_TOPIC, RabbitMQConstant.ROUTING_KEY_USER_IMPORTANT_WARN, "topic important warn: " + request.getMessage());
        rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_TOPIC, RabbitMQConstant.ROUTING_KEY_USER_IMPORTANT_ERROR, "topic important error: " + request.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/topic-direct/publish")
    public ResponseEntity<String> publishDirectExchangeMessage(@RequestBody RabbitMQPublishRequest request) {
        String response = "Done sending topic-direct message: " + request.getMessage();
        rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_DIRECT, "routing-key-1", request.getMessage());
        rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_DIRECT, "routing-key-2", request.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/topic-fanout/publish")
    public ResponseEntity<String> publishFanoutExchangeMessage(@RequestBody RabbitMQPublishRequest request) {
        String response = "Done sending topic-fanout message: " + request.getMessage();
        rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_FANOUT, StringUtils.EMPTY, request.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
