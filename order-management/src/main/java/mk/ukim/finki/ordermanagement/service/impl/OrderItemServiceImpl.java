package mk.ukim.finki.ordermanagement.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.ordermanagement.domain.dtos.request.AddProductToOrderDto;
import mk.ukim.finki.ordermanagement.domain.dtos.request.RemoveProductFromOrderDto;
import mk.ukim.finki.ordermanagement.domain.models.Order;
import mk.ukim.finki.ordermanagement.domain.models.OrderItem;
import mk.ukim.finki.ordermanagement.repository.OrderItemRepository;
import mk.ukim.finki.ordermanagement.service.OrderItemService;
import mk.ukim.finki.ordermanagement.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final RestTemplate restTemplate;

    @Override
    public OrderItem addProductToOrder(AddProductToOrderDto dto) {
        Order order = this.orderService.findPendingOrderForUser(dto.getUserId());
        OrderItem orderItem = this.orderItemRepository
                .findOrderItemByOrderIdAndSizeIdAndIsDeletedFalseAndProductId(order.getId(), dto.getSizeId(), dto.getProductId())
                .orElse(null);
        if(orderItem == null) {
            Float productPrice = this.restTemplate
                    .getForObject(String.format("http://PRODUCT-CATALOG-SERVICE/products/price/%s", dto.getProductId()),
                            Float.class);
            return this.orderItemRepository.save(new OrderItem(order, dto.getProductId(), productPrice, dto.getSizeId(), dto.getQuantity()));
        }
        else {
            orderItem.setQuantity(orderItem.getQuantity() + dto.getQuantity());
            return this.orderItemRepository.save(orderItem);
        }
    }

    @Override
    public void removeProductFromOrder(RemoveProductFromOrderDto dto) {
        Order order = this.orderService.findPendingOrderForUser(dto.getUserId());
        OrderItem orderItem = this.orderItemRepository
                .findOrderItemByOrderIdAndSizeIdAndIsDeletedFalseAndProductId(order.getId(), dto.getSizeId(), dto.getProductId())
                .orElse(null);
        if(orderItem != null) {
            orderItem.setIsDeleted(true);
            this.orderItemRepository.save(orderItem);
        }
    }
}
