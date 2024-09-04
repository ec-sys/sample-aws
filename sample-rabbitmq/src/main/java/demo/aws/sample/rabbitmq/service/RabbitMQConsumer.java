package demo.aws.sample.rabbitmq.service;

import demo.aws.sample.rabbitmq.domain.constant.RabbitMQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQConsumer {

    @RabbitListener(queues = RabbitMQConstant.QUEUE_DIRECT_1)
    public void receiveMessageDirectQueue1(String message) {
        log.info("direct-queue-1 received message: {}", message);
    }

    @RabbitListener(queues = RabbitMQConstant.QUEUE_DIRECT_2)
    public void receiveMessageDirectQueue2(String message) {
        log.info("direct-queue-2 received message: {}", message);
    }

    @RabbitListener(queues = RabbitMQConstant.QUEUE_FANOUT_1)
    public void receiveMessageFanoutQueue1(String message) {
        log.info("fanout-queue-1 received message: {}", message);
    }

    @RabbitListener(queues = RabbitMQConstant.QUEUE_FANOUT_2)
    public void receiveMessageFanoutQueue2(String message) {
        log.info("fanout-queue-2 received message: {}", message);
    }

    @RabbitListener(queues = RabbitMQConstant.QUEUE_TOPIC_1)
    public void receiveMessageTopicQueue1(String message) {
        log.info("topic-queue-1 received message: {}", message);
    }

    @RabbitListener(queues = RabbitMQConstant.QUEUE_TOPIC_2)
    public void receiveMessageTopicQueue2(String message) {
        log.info("topic-queue-2 received message: {}", message);
    }
}
