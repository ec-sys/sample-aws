package demo.aws.sample.kafka.service;

import demo.aws.sample.kafka.api.request.KafkaPublishRequest;
import demo.aws.sample.kafka.domain.constant.KafkaConstant;
import demo.aws.sample.kafka.domain.dto.KafkaProcessRequestDto;
import demo.aws.sample.kafka.domain.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class KafkaPublisherService {
    @Autowired
    private KafkaTemplate<String, KafkaProcessRequestDto> kafkaTemplate;

    public String publishMessage(KafkaPublishRequest request) {
        UUID uuid = UUID.randomUUID();
        KafkaProcessRequestDto requestDto = new KafkaProcessRequestDto();
        requestDto.setOrderId(uuid.toString());
        requestDto.setStatus(OrderStatus.NEW);
        requestDto.setPayload(request.getMessage());
        CompletableFuture<SendResult<String, KafkaProcessRequestDto>> future =
                kafkaTemplate.send(KafkaConstant.TOPIC_ORDER, String.valueOf(requestDto.getOrderId()), requestDto);
        log.info("Sent: {}", requestDto);
        return requestDto.getOrderId();
    }
}
