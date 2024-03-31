package j2ee.project.repository;

import j2ee.project.models.Payment;
import j2ee.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("SELECT p FROM Payment p WHERE p.HashID = ?1 ")
    public Payment findByHashID(String hashID);

    @Query("SELECT p FROM Payment p WHERE p.TransactionID = ?1 ")
    public Payment findByTransactionID(String transactionID);
}
