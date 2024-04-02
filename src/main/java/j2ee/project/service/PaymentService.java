package j2ee.project.service;

import j2ee.project.models.Payment;
import j2ee.project.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment getPaymentByHashID(String hashID) {
        return paymentRepository.findByHashID(hashID);
    }

    public Payment getPaymentByTransactionID(String transactionID) {
        return paymentRepository.findByTransactionID(transactionID);
    }

    public void addBill(Payment payment) {
        paymentRepository.save(payment);
    }

    public void updateBill(Payment payment) {
        paymentRepository.save(payment);
    }
}
