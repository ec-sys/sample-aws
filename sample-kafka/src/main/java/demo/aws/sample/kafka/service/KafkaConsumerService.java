package demo.aws.sample.kafka.service;

import demo.aws.sample.kafka.domain.constant.KafkaConstant;
import demo.aws.sample.kafka.domain.dto.KafkaProcessRequestDto;
import demo.aws.sample.kafka.domain.dto.KafkaProcessResponseDto;
import demo.aws.sample.kafka.domain.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {
    @Autowired
    private KafkaTemplate<String, KafkaProcessResponseDto> kafkaTemplate;

    @KafkaListener(id = KafkaConstant.LISTENER_CREATE_ORDER, topics = KafkaConstant.TOPIC_ORDER, groupId = KafkaConstant.GROUP_CUSTOMER)
    public void onCreateOrderEvent(KafkaProcessRequestDto requestDto) {
        log.info("Received: {}" , requestDto);
        if(OrderStatus.NEW.equals(requestDto.getStatus())) {
            confirm(requestDto);
        }
    }

    public void confirm(KafkaProcessRequestDto requestDto) {
        KafkaProcessResponseDto responseDto = new KafkaProcessResponseDto();
        responseDto.setOrderId(requestDto.getOrderId());
        responseDto.setStatus(OrderStatus.COMPLETED);
        responseDto.setPayload(requestDto.getPayload());
        kafkaTemplate.send(KafkaConstant.TOPIC_RESULT_ORDER, String.valueOf(requestDto.getOrderId()), responseDto);
        log.info("Sent: {}", responseDto);
    }
}
