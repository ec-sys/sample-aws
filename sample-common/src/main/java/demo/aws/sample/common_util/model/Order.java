package demo.aws.sample.common_util.model;

import lombok.Data;

@Data
public class Order {

    private String id;
    private Long sourceAccountId;
    private Long targetAccountId;
    private int amount;
    private String status;
    private Long groupId;

    public Order() {
    }

    public Order(String id, Long sourceAccountId, Long targetAccountId, int amount, String status, Long groupId) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.status = status;
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", sourceAccountId=" + sourceAccountId +
                ", targetAccountId=" + targetAccountId +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}
