package j2ee.project.POJO;


import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    // Thêm sản phẩm vào giỏ hàng hoặc cập nhật số lượng nếu sản phẩm đã tồn tại
    public void addProduct(Integer productId, Integer quantity, double price) {
        for (CartItem item : items) {
            if (item.getProductId() == productId) {
                item.setQuantity(item.getQuantity() + quantity);
                item.setPrice(price);
                return;
            }
        }
        items.add(new CartItem(productId, quantity, price));
    }


    // Xóa sản phẩm khỏi giỏ hàng
    public void removeProduct(Integer productId) {
        items.removeIf(item -> (item.getProductId() == productId));
    }

    // Kiểm tra giỏ hàng có trống hay không
    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Lấy danh sách sản phẩm trong giỏ hàng
    public List<CartItem> getItems() {
        return items;
    }
}

