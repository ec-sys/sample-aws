package demo.aws.sample.kafka.domain.dto;

import demo.aws.sample.kafka.domain.model.OrderStatus;
import lombok.Data;

@Data
public class KafkaProcessResponseDto {
    private String orderId;
    private OrderStatus status;
    private String payload;
}
