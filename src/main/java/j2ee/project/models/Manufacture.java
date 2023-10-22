package j2ee.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.Date;

@Table(name = "manufacture")
@Entity
public class Manufacture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Name is mandatory")
    @NotNull(message = "Name is mandatory")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Address is mandatory")
    private String address;

    @Column(nullable = false)
    @NotBlank(message = "Phone is mandatory")
    private String phone;

    @Column(nullable = true)
    private String imagePath;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    //get set
    public Manufacture() {
    }

    public Manufacture(Integer id, String name, String address, String phone, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getImagePath(){
        return this.imagePath;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getCreatedAt() {
        if (this.createdAt == null) {
            return null;
        }
        //return formrta yyyy-mm-dd hh:mm:ss
        return this.createdAt.toString();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @PrePersist
    private void setCreatedAt() {
        // Set the createdAt field to the current timestamp when persisting the entity
        createdAt = new Timestamp(new Date().getTime());
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }
    @Override
    public String toString() {
        return String.format("{id:%d, name:'%s', address:'%s', phone:'%s', createdAt:'%s'}",
                this.id, this.name, this.address, this.phone, this.getCreatedAt());
    }

}
