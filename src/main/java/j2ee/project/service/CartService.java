package j2ee.project.service;

import j2ee.project.models.Cart;
import j2ee.project.repository.CartRepository;
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
    public Optional<Cart> findCartByProductId(int productId) {
        return cartRepository.findByProductId(productId);
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
   
}
