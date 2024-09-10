package demo.aws.sample.kafka.api.controller;

import demo.aws.sample.kafka.api.request.KafkaPublishRequest;
import demo.aws.sample.kafka.service.KafkaPublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
@Slf4j
public class KafkaProducerController {
    @Autowired
    KafkaPublisherService publisherService;

    @PostMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestBody KafkaPublishRequest request) {
        String orderId = publisherService.publishMessage(request);
        String response = "Done sending message: " + request.getMessage() + ", with orderId: " + orderId;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
