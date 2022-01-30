package mk.ukim.finki.ordermanagement.service;

import mk.ukim.finki.ordermanagement.domain.models.Order;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetOrderItemDto;

import java.util.List;

public interface OrderService {
    Order findPendingOrderForUser(Long userId);

    List<GetOrderItemDto> getOrderItems(Long userId);

    Boolean isProductInShoppingCart(Long userId, Long productId);

    Integer getItemsInBagNumber(Long userId);

    void save(Order order);
}
