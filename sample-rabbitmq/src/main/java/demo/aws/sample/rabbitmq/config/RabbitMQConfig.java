package demo.aws.sample.rabbitmq.config;

import demo.aws.sample.rabbitmq.domain.constant.RabbitMQConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // direct exchange
    @Bean(name = "directExchange")
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitMQConstant.EXCHANGE_DIRECT);
    }
    @Bean(name = "directQueue1")
    public Queue directQueue1() {
        return new Queue(RabbitMQConstant.QUEUE_DIRECT_1);
    }
    @Bean(name = "directQueue2")
    public Queue directQueue2() {
        return new Queue(RabbitMQConstant.QUEUE_DIRECT_2);
    }
    @Bean
    public Binding bindingDirectQueue1(@Qualifier("directQueue1")Queue queue, @Qualifier("directExchange")DirectExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with("routing-key-1");
    }
    @Bean
    public Binding bindingDirectQueue2(@Qualifier("directQueue2")Queue queue, @Qualifier("directExchange")DirectExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with("routing-key-2");
    }

    // fanout exchange
    @Bean(name = "fanoutQueue1")
    public Queue fanoutQueue1() {
        return new Queue(RabbitMQConstant.QUEUE_FANOUT_1);
    }
    @Bean(name = "fanoutQueue2")
    public Queue fanoutQueue2() {
        return new Queue(RabbitMQConstant.QUEUE_FANOUT_2);
    }
    @Bean(name = "fanoutExchange")
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitMQConstant.EXCHANGE_FANOUT);
    }
    @Bean
    public Binding bindingFanoutQueue1(@Qualifier("fanoutQueue1")Queue queue, @Qualifier("fanoutExchange")FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
    @Bean
    public Binding bindingFanoutQueue2(@Qualifier("fanoutQueue2")Queue queue, @Qualifier("fanoutExchange")FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    // topic exchange
    @Bean(name = "topicQueue1")
    public Queue topicQueue1() {
        return new Queue(RabbitMQConstant.QUEUE_TOPIC_1);
    }
    @Bean(name = "topicQueue2")
    public Queue topicQueue2() {
        return new Queue(RabbitMQConstant.QUEUE_TOPIC_2);
    }
    @Bean(name = "topicExchange")
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitMQConstant.EXCHANGE_TOPIC);
    }
    @Bean
    public Binding bindingTopicQueue1(@Qualifier("topicQueue1")Queue queue, @Qualifier("topicExchange")TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("*.important.*");
    }
    @Bean
    public Binding bindingTopicQueue2(@Qualifier("topicQueue2")Queue queue, @Qualifier("topicExchange")TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("#.error");
    }

//    @Bean
//    public MessageConverter converter() {
//        return new Jackson2JsonMessageConverter();
//    }

//
//    @Bean
//    public AmqpTemplate template(ConnectionFactory connectionFactory) {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(converter());
//        return rabbitTemplate;
//    }

//    @Bean
//    public Queue queue() {
//        return new Queue("queue-name", false);
//    }
//
//    @Bean
//    public Exchange exchange() {
//        return new DirectExchange("exchange-name");
//    }
//
//    @Bean
//    public Binding binding(Queue queue, Exchange exchange) {
//        return BindingBuilder.bind(queue)
//                .to(exchange)
//                .with("routing-key")
//                .noargs();
//    }
}
