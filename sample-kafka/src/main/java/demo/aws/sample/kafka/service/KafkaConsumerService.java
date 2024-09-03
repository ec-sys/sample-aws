package demo.aws.sample.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {
    @Autowired
    private KafkaTemplate<String, OrderProcessResponseDto> kafkaTemplate;

    @KafkaListener(id = "create-orders", topics = "create-orders", groupId = "customer")
    public void onCreateOrderEvent(OrderProcessRequestDto requestDto) {
        log.info("Received: {}" , requestDto);
        if(OrderStatus.NEW.equals(requestDto.getStatus())) {
            reserve(requestDto);
        } else {
            confirm(requestDto);
        }
    }
}
