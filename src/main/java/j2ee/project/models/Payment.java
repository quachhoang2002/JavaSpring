package j2ee.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.Date;

@Table(name = "payments")
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private Integer UserID ;

    @NotNull
    private Integer orderID;

    @NotNull
    private String TransactionID;

    @NotNull
    private String HashID;
    private String Info;

    @NotNull
    private int Amount;

    // -1: failed, 0: pending, 1: success
    @NotNull
    private int Status;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
    public Payment() {
    }

    public Payment(int id,String info,int amount, Integer UserID,Integer OrderID, String TransactionID, String HashID, int Status, String Info) {
        this.id = id;
        this.Amount = amount;
        this.Info = info;
        this.orderID = OrderID;
        this.UserID = UserID;
        this.TransactionID = TransactionID;
        this.HashID = HashID;
        this.Status = Status;
        this.Info = Info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserID() {
        return UserID;
    }

    public void setUserID(Integer UserID) {
        this.UserID = UserID;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String TransactionID) {
        this.TransactionID = TransactionID;
    }

    public String getHashID() {
        return HashID;
    }

    public void setHashID(String HashID) {
        this.HashID = HashID;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String Info) {
        this.Info = Info;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int Amount) {
        this.Amount = Amount;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public String getCreatedAt() {
        if (createdAt == null) {
            return null;
        }
        return createdAt.toString();
    }

    @PrePersist
    public void setCreatedAt() {
        createdAt = new Timestamp(new Date().getTime());
    }

    public String getUpdatedAt() {
        if (updatedAt == null) {
            return null;
        }
        return updatedAt.toString();
    }

    @PreUpdate
    public void setUpdatedAt() {
        updatedAt = new Timestamp(new Date().getTime());
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", UserID=" + UserID +
                ", TransactionID=" + TransactionID +
                ", HashID=" + HashID +
                ", Status=" + Status +
                ", Info=" + Info +
                ", Amount=" + Amount +
                ", createdAt=" + this.getCreatedAt() +
                ", updatedAt=" + this.getUpdatedAt() +
                '}';
    }
}
