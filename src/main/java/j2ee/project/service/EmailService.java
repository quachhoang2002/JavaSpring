package j2ee.project.service;

import j2ee.project.models.Cart;
import j2ee.project.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class EmailService {
    @Autowired
    private ProductService productService;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOrderConfirmationEmail(String emailAddress, String name, String deliveryAddress, String phoneNumber, List<Cart> cartList, double totalMoneyVAT) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("minhtri180522@gmail.com"); // Email nguồn
        message.setTo(emailAddress); // Email đích
        message.setSubject("Đặt hàng thành công từ Ronaldo Family");

        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(name).append("\n");
        sb.append("Bạn vừa đặt hàng từ Ronaldo Family.\n");
        sb.append("Địa chỉ nhận hàng của bạn là: ").append(deliveryAddress).append("\n");
        sb.append("Số điện thoại khi nhận hàng của bạn là: ").append(phoneNumber).append("\n");
        sb.append("Các sản phẩm bạn đã đặt là:\n");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        for (Cart cart : cartList) {
            productService.getProductById(cart.getProductId()).ifPresent(product -> {
                sb.append(product.getName()).append(" | Price: ").append(currencyFormat.format(product.getPrice())).append(" | Amount: ").append(cart.getQuantity()).append("\n");
            });
        }
        sb.append("Tổng Tiền: ").append(currencyFormat.format(totalMoneyVAT)).append("\n");
        sb.append("Cảm ơn bạn đã đặt hàng tại Ronaldo Family.\n");
        sb.append("Ký tên: Ronaldo");

        message.setText(sb.toString());
        javaMailSender.send(message);
    }
}
