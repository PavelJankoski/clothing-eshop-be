package mk.ukim.finki.ordermanagement.service;

public interface OrderService {
    Boolean isProductInShoppingCart(Long userId, Long productId);
}
