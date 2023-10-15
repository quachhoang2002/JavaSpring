package j2ee.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.Date;

@Table(name = "products")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false)
    private double price;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false ,referencedColumnName = "id")
    private Category category;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "manufacture_id",nullable = false ,referencedColumnName = "id")
    private Manufacture manufacture;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "image", nullable = false)
    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

}