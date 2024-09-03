package demo.aws.sample.kafka.api.controller;

import demo.aws.sample.kafka.api.request.KafkaPublishRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
@Slf4j
public class KafkaProducerController {
    @PostMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestBody KafkaPublishRequest request) {
        String response = "Done sending message: " + request.getMessage();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
