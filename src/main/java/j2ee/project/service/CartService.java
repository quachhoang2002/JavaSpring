package j2ee.project.service;

import j2ee.project.models.Cart;
import j2ee.project.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addCart(Cart cart){
        cartRepository.save(cart);
    }

   
}
