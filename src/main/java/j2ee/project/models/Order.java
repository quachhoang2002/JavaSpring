package j2ee.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String customerName;
    @NotNull
    private String shippingAddress;
    @NotNull
    private String customerPhone;
    @NotNull
    private String email_receive;
    @NotNull
    private double total_price;

    @NotNull
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;
    private int status;

    public Order(int id, String customerName, String shippingAddress, String customerPhone, String email_receive, double total_price, int status) {
        this.id = id;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.customerPhone = customerPhone;
        this.email_receive = email_receive;
        this.total_price = total_price;
        this.status = status;
    }

    public Order(int id, String customerName, String shippingAddress, String customerPhone, int status) {
        this.id = id;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.customerPhone = customerPhone;
        this.status = status;
    }


    public Order() {
    }

    public Order(int id, String customerName, String shippingAddress, String customerPhone) {
        this.id = id;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.customerPhone = customerPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail_receive() {
        return email_receive;
    }

    public void setEmail_receive(String email_receive) {
        this.email_receive = email_receive;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
}