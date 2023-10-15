package j2ee.project.models;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Timestamp;

@Table(name = "user_entity")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "createdat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdat;

    @Column(name = "status", nullable = true, columnDefinition = "INT DEFAULT 1")
    private Integer status;

    //token
    @Column(name = "token", nullable = true)
    private String token;

    public User() {
    }

    public User(Integer id, String name, String email, String password, String phone, Timestamp createdAt, Integer status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.createdat = createdAt;
        this.status = status;
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

    public String getCreateat() {
        return this.createdat.toString().substring(0, 19);
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
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
            ", createdat='" + getCreateat()+ "'" +
            ", status='" + getStatus() + "'" +
            ", token='" + getToken() + "'" +
            "}";
    }


}
