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
    private int status;

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
}
