package j2ee.project.models;

import jakarta.persistence.*;
import java.util.Date;

@Table(name = "cart_entity")
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "productId", nullable = false)
    private int productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "createdAt", nullable = false)
    @Temporal(TemporalType.DATE) // Sử dụng @Temporal để chỉ định kiểu dữ liệu của trường ngày tháng
    private Date createdAt;

    // Constructors, getters, setters, và các phương thức khác

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
