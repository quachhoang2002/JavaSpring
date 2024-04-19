package j2ee.project.controller;


import j2ee.project.DTO.SubmitOrderDTO;
import j2ee.project.VNPay.Response;
import j2ee.project.VNPay.VNPayService;
import j2ee.project.models.Order;
import j2ee.project.models.Payment;
import j2ee.project.models.User;
import j2ee.project.service.OrderService;
import j2ee.project.service.PaymentService;
import j2ee.project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController extends Controller {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService UserService;


    @Autowired
    private OrderService orderService;


    @PostMapping("/submit")
    public ResponseEntity<?> submidOrder(@Valid  @RequestBody SubmitOrderDTO submitOrderDTO,
                                         BindingResult bindingResultm,
                                         @RequestHeader("Token") String token,
                                         HttpServletRequest request) {
        if (bindingResultm.hasErrors()) {
            return this.errorResponse(bindingResultm.getAllErrors().get(0).getDefaultMessage());
        }

        String orderInfo = submitOrderDTO.getOrderInfo();
        Integer orderID = submitOrderDTO.getOrderID();
        Order order = orderService.getOrderById(orderID);
        if (order == null) {
            return this.errorResponse("Order not found");
        }
//        if (order.getStatus() == 1) {
//            return this.errorResponse("Order already confirmed");
//        }
        int orderTotal = (int) order.getTotal_price();

        String baseUrl = "https" + "://" + request.getServerName();
        Response resp = VNPayService.createOrder(orderTotal, orderInfo, baseUrl);

        User user = UserService.findByTokenAndCheck(token);
        if (user == null) {
            return this.errorResponse("User not found");
        }


        // Save payment to database
        Payment payment = new Payment();
        payment.setHashID(resp.hashID);
        payment.setTransactionID(resp.transactionID);
        payment.setInfo(orderInfo);
        payment.setAmount(orderTotal);
        payment.setStatus(0);
        payment.setOrderID(orderID);
        payment.setUserID(user.getId());
        try {
            paymentService.addBill(payment);
        } catch (Exception e) {
            return this.errorResponse(e.getMessage());
        }

        return this.successResponse("Success", resp);
    }

    @GetMapping("/confirm")
    public String payment(@RequestParam("vnp_Amount") long vnpAmount,
                          @RequestParam("vnp_BankCode") String vnpBankCode,
                          @RequestParam("vnp_BankTranNo") String vnpBankTranNo,
                          @RequestParam("vnp_CardType") String vnpCardType,
                          @RequestParam("vnp_OrderInfo") String vnpOrderInfo,
                          @RequestParam("vnp_PayDate") String vnpPayDate,
                          @RequestParam("vnp_ResponseCode") String vnpResponseCode,
                          @RequestParam("vnp_TmnCode") String vnpTmnCode,
                          @RequestParam("vnp_TransactionNo") String vnpTransactionNo,
                          @RequestParam("vnp_TransactionStatus") String vnpTransactionStatus,
                          @RequestParam("vnp_TxnRef") String vnpTxnRef,
                          @RequestParam("vnp_SecureHash") String vnpSecureHash) {
        // Your method logic goes here

        System.out.println("vnp_Amount: " + vnpAmount);
        System.out.println("vnp_BankCode: " + vnpBankCode);
        System.out.println("vnp_BankTranNo: " + vnpBankTranNo);
        System.out.println("vnp_CardType: " + vnpCardType);
        System.out.println("vnp_OrderInfo: " + vnpOrderInfo);
        System.out.println("vnp_PayDate: " + vnpPayDate);
        System.out.println("vnp_ResponseCode: " + vnpResponseCode);
        System.out.println("vnp_TmnCode: " + vnpTmnCode);
        System.out.println("vnp_TransactionNo: " + vnpSecureHash);


        if (!vnpResponseCode.equals("00")) {
            return "Invalid request";
        }

        Payment payment = paymentService.getPaymentByTransactionID(vnpTxnRef);
        if (payment == null) {
            return "Payment not found";
        }
        Order order = orderService.getOrderById(payment.getOrderID());
        if (order == null) {
            return "Order not found";
        }
//        if (payment.getStatus() == 1) {
//            return "Payment already confirmed";
//        }
//
//        if (order.getStatus() == 1) {
//            return "Order already confirmed";
//        }

        payment.setStatus(1);
        paymentService.updateBill(payment);

        order.setStatus(1);
        orderService.updateOrder(order);


        // Example:
        return "Received payment with amount: " + vnpAmount + " and order info: " + vnpOrderInfo;
    }

}
