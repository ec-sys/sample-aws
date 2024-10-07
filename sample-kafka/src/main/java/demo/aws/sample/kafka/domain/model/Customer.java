package demo.aws.sample.kafka.domain.model;

public class Customer {
    private final int customerID;
    private final String customerName;

    public Customer(int ID, String name) {
        this.customerID = ID;
        this.customerName = name;
    }

    public int getID() {
        return customerID;
    }

    public String getName() {
        return customerName;
    }
}