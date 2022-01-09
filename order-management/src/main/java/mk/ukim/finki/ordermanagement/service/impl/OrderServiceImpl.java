package mk.ukim.finki.ordermanagement.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.ordermanagement.domain.enums.OrderStatus;
import mk.ukim.finki.ordermanagement.domain.models.Order;
import mk.ukim.finki.ordermanagement.repository.OrderRepository;
import mk.ukim.finki.ordermanagement.service.OrderService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order findPendingOrderForUser(Long userId) {
        Order order = this.orderRepository.findOrderByUserIdAndStatusAndIsDeletedFalse(userId, OrderStatus.PENDING)
                .orElse(null);
        if(order == null) {
            return this.orderRepository.save(new Order(userId));
        }
        return order;
    }

    @Override
    public Boolean isProductInShoppingCart(Long userId, Long productId) {
        return this.orderRepository.isProductInShoppingCart(userId, productId).getExists();
    }
}
