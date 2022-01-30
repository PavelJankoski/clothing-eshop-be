package mk.ukim.finki.ordermanagement.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.ordermanagement.domain.dtos.request.AddProductToOrderDto;
import mk.ukim.finki.ordermanagement.domain.dtos.request.ChangeOrderItemQuantityDto;
import mk.ukim.finki.ordermanagement.domain.dtos.request.RemoveProductFromOrderDto;
import mk.ukim.finki.ordermanagement.domain.exceptions.OrderItemNotFoundException;
import mk.ukim.finki.ordermanagement.domain.models.Order;
import mk.ukim.finki.ordermanagement.domain.models.OrderItem;
import mk.ukim.finki.ordermanagement.repository.OrderItemRepository;
import mk.ukim.finki.ordermanagement.service.OrderItemService;
import mk.ukim.finki.ordermanagement.service.OrderService;
import mk.ukim.finki.sharedkernel.domain.dto.request.ProductSizeDto;
import mk.ukim.finki.sharedkernel.kafka.KafkaTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, ProductSizeDto> kafkaTemplate;

    @Override
    public void addProductToOrder(AddProductToOrderDto dto) {
        Order order = this.orderService.findPendingOrderForUser(dto.getUserId());
        OrderItem orderItem = this.orderItemRepository
                .findOrderItemByOrderIdAndSizeIdAndIsDeletedFalseAndProductId(order.getId(), dto.getSizeId(), dto.getProductId())
                .orElse(null);
        if(orderItem == null) {
            Float productPrice = this.restTemplate
                    .getForObject(String.format("http://PRODUCT-CATALOG-SERVICE/products/price/%s", dto.getProductId()),
                            Float.class);
            this.orderItemRepository.save(new OrderItem(order, dto.getProductId(), productPrice, dto.getSizeId(), dto.getQuantity()));
        }
        else {
            orderItem.setQuantity(orderItem.getQuantity() + dto.getQuantity());
            this.orderItemRepository.save(orderItem);
        }
        kafkaTemplate.send(KafkaTopics.CHANGE_PRODUCT_SIZE_QUANTITY, new ProductSizeDto(dto.getProductId(), dto.getSizeId(), -dto.getQuantity()));
    }

    @Override
    public void removeProductFromOrder(RemoveProductFromOrderDto dto) {
        OrderItem orderItem = this.findOrderItem(dto.getUserId(), dto.getSizeId(), dto.getProductId());
        orderItem.setIsDeleted(true);
        this.orderItemRepository.save(orderItem);
        kafkaTemplate.send(KafkaTopics.CHANGE_PRODUCT_SIZE_QUANTITY, new ProductSizeDto(dto.getProductId(), dto.getSizeId(), orderItem.getQuantity()));
    }

    @Override
    public void changeQuantity(ChangeOrderItemQuantityDto dto) {
        OrderItem orderItem = this.findOrderItem(dto.getUserId(), dto.getSizeId(), dto.getProductId());
        Integer oldQuantity = orderItem.getQuantity();
        orderItem.setQuantity(dto.getQuantity());
        this.orderItemRepository.save(orderItem);
        kafkaTemplate.send(KafkaTopics.CHANGE_PRODUCT_SIZE_QUANTITY, new ProductSizeDto(dto.getProductId(), dto.getSizeId(), oldQuantity - dto.getQuantity()));
    }

    private OrderItem findOrderItem(Long userId, Long sizeId, Long productId) throws OrderItemNotFoundException {
        Order order = this.orderService.findPendingOrderForUser(userId);
        return this.orderItemRepository
                .findOrderItemByOrderIdAndSizeIdAndIsDeletedFalseAndProductId(order.getId(), sizeId, productId)
                .orElseThrow(OrderItemNotFoundException::new);
    }
}
