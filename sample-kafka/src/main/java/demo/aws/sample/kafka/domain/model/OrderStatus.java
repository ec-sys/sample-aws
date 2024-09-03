package demo.aws.sample.kafka.domain.model;

public enum OrderStatus {
    NEW,
    CREATING,
    CREATED,
    SHIPPING,
    SHIPPED,
    PAID,
    COMPLETED,
    CANCELLED,
    REJECTED,
    ROLLBACK;
}
