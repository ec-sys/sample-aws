package demo.aws.sample.rabbitmq.domain.constant;

public class RabbitMQConstant {

    public final static String ROUTING_KEY_USER_IMPORTANT_WARN = "user.important.warn";
    public final static String ROUTING_KEY_USER_IMPORTANT_ERROR = "user.important.error";

    // direct exchange
    public final static String EXCHANGE_DIRECT = "exchange-direct-1";
    public final static String QUEUE_DIRECT_1 = "queue-direct-1";
    public final static String QUEUE_DIRECT_2 = "queue-direct-2";

    // fanout exchange
    public final static String EXCHANGE_FANOUT = "exchange-fanout-1";
    public final static String QUEUE_FANOUT_1 = "queue-fanout-1";
    public final static String QUEUE_FANOUT_2 = "queue-fanout-2";

    // topic exchange
    public final static String EXCHANGE_TOPIC = "exchange-topic-1";
    public final static String QUEUE_TOPIC_1 = "queue-topic-1";
    public final static String QUEUE_TOPIC_2 = "queue-topic-2";
}
