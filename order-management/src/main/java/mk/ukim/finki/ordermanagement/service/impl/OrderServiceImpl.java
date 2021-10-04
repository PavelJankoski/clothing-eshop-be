package mk.ukim.finki.ordermanagement.service.impl;

import mk.ukim.finki.ordermanagement.repository.OrderRepository;
import mk.ukim.finki.ordermanagement.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Boolean isProductInShoppingCart(Long userId, Long productId) {
        return this.orderRepository.isProductInShoppingCart(userId, productId).getExists();
    }
}
