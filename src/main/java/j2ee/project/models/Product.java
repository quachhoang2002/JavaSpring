package j2ee.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.Date;

@Table(name = "products")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Name is mandatory")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Price is mandatory")
    @Column(name = "price", nullable = false)
    private double price;

    @NotNull(message = "Category is mandatory")
    @ManyToOne
    @JoinColumn(name = "category_id" ,referencedColumnName = "id")
    private Category category;

    @NotNull(message = "Manufacture is mandatory")
    @ManyToOne
    @JoinColumn(name = "manufacture_id",referencedColumnName = "id")
    private Manufacture manufacture;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "imagePath", nullable = true)
    private String imagePath;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updatedAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Manufacture getManufacture() {
        return manufacture;
    }

    public void setManufacture(Manufacture manufacture) {
        this.manufacture = manufacture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String image) {
        this.imagePath = image;
    }

    @PrePersist
    private void setCreatedAt() {
        // Set the createdAt field to the current timestamp when persisting the entity
        createdAt = new Timestamp(new Date().getTime());
    }

    public String getCreatedAt() {
        if (createdAt == null) {
            return null;
        }
        return createdAt.toString().substring(0, 19);
    }

    @PreUpdate
    private void setUpdatedAt() {
        // Set the updatedAt field to the current timestamp when persisting the entity
        updatedAt = new Timestamp(new Date().getTime());
    }

    public String getUpdatedAt() {
        if (updatedAt == null) {
            return null;
        }
        return updatedAt.toString().substring(0, 19);
    }

    //to string
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name +
                ", price='" + price +
                ", category='" + category +
                ", manufacture='" + manufacture +
                ", description='" + description +
                ", imagePath='" + imagePath +
                ", createdAt='" + createdAt +
                ", updatedAt='" + updatedAt +
                '}';
    }

}
