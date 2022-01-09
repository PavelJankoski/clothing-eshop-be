package mk.ukim.finki.ordermanagement.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.ordermanagement.domain.enums.OrderStatus;
import mk.ukim.finki.ordermanagement.domain.models.Order;
import mk.ukim.finki.ordermanagement.repository.OrderRepository;
import mk.ukim.finki.ordermanagement.service.OrderService;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetOrderItemDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

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
    public List<GetOrderItemDto> getOrderItems(Long userId) {
        Order order = this.findPendingOrderForUser(userId);
        List<GetOrderItemDto> orderItemDtoList = new ArrayList<>();
        order.getOrderItems()
                .stream()
                .filter(oi -> !oi.getIsDeleted())
                .forEach(oi -> {
            GetOrderItemDto orderItemDto = this.restTemplate
                    .getForObject(String.format("http://PRODUCT-CATALOG-SERVICE/products/to-cart-item/%s/%s", oi.getProductId(), oi.getSizeId()),
                            GetOrderItemDto.class);
            if(orderItemDto != null) {
                orderItemDto.setId(oi.getId());
                orderItemDto.setPrice(oi.getPrice());
                orderItemDto.setProductId(oi.getProductId());
                orderItemDto.setSelectedQuantity(oi.getQuantity());
                orderItemDtoList.add(orderItemDto);
            }
        });
        return orderItemDtoList;
    }

    @Override
    public Boolean isProductInShoppingCart(Long userId, Long productId) {
        return this.orderRepository.isProductInShoppingCart(userId, productId).getExists();
    }
}
