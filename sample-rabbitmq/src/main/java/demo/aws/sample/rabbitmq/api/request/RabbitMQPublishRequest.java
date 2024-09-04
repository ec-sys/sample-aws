package demo.aws.sample.rabbitmq.api.request;

import lombok.Data;

@Data
public class RabbitMQPublishRequest {
    private String message;
}
