package demo.aws.sample.kafka.api.request;

import lombok.Data;

@Data
public class KafkaPublishRequest {
    private String message;
}
