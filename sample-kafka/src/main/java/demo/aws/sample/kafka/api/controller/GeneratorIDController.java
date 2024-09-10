package demo.aws.sample.kafka.api.controller;

import demo.aws.sample.common_util.util.SequenceGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uuid")
public class GeneratorIDController {

    @GetMapping("/snowflake")
    public ResponseEntity<String> getSnowflakeId() {
        SequenceGenerator generator = new SequenceGenerator(1);
        String response = String.valueOf(generator.nextId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
