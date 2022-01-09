package mk.ukim.finki.ordermanagement.service;

import mk.ukim.finki.ordermanagement.domain.models.Order;

public interface OrderService {
    Order findPendingOrderForUser(Long userId);

    Boolean isProductInShoppingCart(Long userId, Long productId);
}
