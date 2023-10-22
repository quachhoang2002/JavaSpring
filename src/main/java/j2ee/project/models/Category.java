package j2ee.project.models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Lob
    private String description;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    public Category() {
    }

    // Getters and setters, or use Lombok to generate them.

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return String.format("{id:%d, name:'%s',description:'%s',createdAt:'%s'}",
                this.id, this.name,this.description ,this.getCreatedAt());
    }

}
