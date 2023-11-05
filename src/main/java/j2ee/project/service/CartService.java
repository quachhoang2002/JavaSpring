package j2ee.project.service;

import j2ee.project.models.Cart;
import j2ee.project.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addCart(Cart cart){
        cartRepository.save(cart);
    }
    public Optional<Cart> findCartByProductIdAndUser_Id(int productId, int user_id) {
        return cartRepository.findCartByProductIdAndUser_Id(productId, user_id);
    }
    public Optional<Cart> findCartByProductId(int productId) {
        return cartRepository.findCartByProductId(productId);
    }
    public void updateCart(Cart cartItem) {
        cartRepository.save(cartItem);
    }
    public List<Cart> findCartByUserId(int user_id){
        return cartRepository.findByUserId(user_id);
    }
    public void deleteByProductIdAndUserId(int productId, int user_id ){
        cartRepository.deleteByProductIdAndUserId(productId, user_id);
    }
    @Transactional
    public boolean deleteCartItem(int user_id, int productId) {
        try {
            Optional<Cart> cartItem = cartRepository.findCartByProductIdAndUser_Id(productId, user_id );

            if (cartItem != null) {
                cartRepository.deleteByProductIdAndUserId(productId, user_id);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa sản phẩm khỏi giỏ hàng: " + e.getMessage());
        }
    }
}
