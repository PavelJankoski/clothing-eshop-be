package mk.ukim.finki.ordermanagement.service;

import mk.ukim.finki.ordermanagement.domain.dtos.response.GetOrderHistoryDetailsDto;
import mk.ukim.finki.ordermanagement.domain.dtos.response.GetOrderHistoryDto;
import mk.ukim.finki.ordermanagement.domain.models.Order;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetOrderItemDto;

import java.util.List;

public interface OrderService {
    Order findById(Long id);

    Order findPendingOrderForUser(Long userId);

    List<GetOrderItemDto> getOrderItems(Long userId);

    Boolean isProductInShoppingCart(Long userId, Long productId);

    Integer getItemsInBagNumber(Long userId);

    void save(Order order);

    void placeOrder(Long userId);

    GetOrderHistoryDto findDeliveredOrders(Long userId);

    GetOrderHistoryDetailsDto findDetailsForOrder(Long orderId);
}
