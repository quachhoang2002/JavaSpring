package j2ee.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Table(name = "manufacture")
@Entity
public class Manufacture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name is mandatory")
    @NotNull(message = "Name is mandatory")
    private String name;

    @Column(name = "address", nullable = false)
    @NotBlank(message = "Address is mandatory")
    private String address;

    @Column(name = "phone", nullable = false)
    @NotBlank(message = "Phone is mandatory")
    private String phone;

    @Column(name = "createat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createat;

    //get set
    public Manufacture() {
    }

    public Manufacture(Integer id, String name, String address, String phone, Timestamp createat) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.createat = createat;
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

    public String getPhone() {
        return this.phone;
    }

    public Timestamp getCreateat() {
        return this.createat;
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

    public void setCreateat(Timestamp createat) {
        this.createat = createat;
    }

    @Override
    public String toString() {
        return String.format("{id:%d, name:'%s', address:'%s', phone:'%s', createat:'%s'}",
                this.id, this.name, this.address, this.phone, this.createat);
    }

}
