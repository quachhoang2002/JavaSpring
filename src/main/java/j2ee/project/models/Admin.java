package j2ee.project.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.Date;

@Table(name = "admin")
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Email is mandatory")
    private String email;

    @NotNull(message = "Password is mandatory")
    private String password;

    @Column(nullable = true)
    private String phone;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdat;

    @Column(nullable = true, columnDefinition = "INT DEFAULT 1")
    private Integer role;

    //token
    @Column(nullable = true)
    private String token;

    public Admin() {
    }

    public Admin(Integer id, String name, String email, String password, String phone, Timestamp createdAt, Integer role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.createdat = createdAt;
        this.role = role;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getCreatedAt() {
        if (createdat == null) {
            return null;
        }
        return createdat.toString().substring(0, 19);
    }

    @PrePersist
    private void setCreatedAt() {
        // Set the createdAt field to the current timestamp when persisting the entity
        createdat = new Timestamp(new Date().getTime());
    }

    public Integer getRole() {
        return this.role;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    //to string
    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", email='" + getEmail() + "'" +
                ", password='" + getPassword() + "'" +
                ", phone='" + getPhone() + "'" +
                ", role='" + getRole() + "'" +
                ", createdAt='" + getCreatedAt()+ "'" +
                ", token='" + getToken() + "'" +
                "}";
    }


}
