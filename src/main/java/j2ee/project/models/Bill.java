package j2ee.project.models;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

@Table(name = "bill_entity")
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "customerName", nullable = false)
    private String customerName;
    @NotNull
    @Column(name = "shippingAddress", nullable = false)
    private String shippingAddress;
    @NotNull
    @Column(name = "customerPhone", nullable = false)
    private String customerPhone;
    @NotNull
    @Column(name = "email_receive", nullable = false)
    private String email_receive;
    @NotNull
    @Column(name = "total_price", nullable = false)
    private double total_price;
    private int status;

    public Bill(int id, String customerName, String shippingAddress, String customerPhone, String email_receive, double total_price, int status) {
        this.id = id;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.customerPhone = customerPhone;
        this.email_receive = email_receive;
        this.total_price = total_price;
        this.status = status;
    }

    public Bill(int id, String customerName, String shippingAddress, String customerPhone, int status) {
        this.id = id;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.customerPhone = customerPhone;
        this.status = status;
    }


    public Bill() {
    }

    public Bill(int id, String customerName, String shippingAddress, String customerPhone) {
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
