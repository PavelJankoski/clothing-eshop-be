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

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;

    @Override
    public OrderItem addProductToOrder(AddProductToOrderDto dto) {
        Order order = this.orderService.findPendingOrderForUser(dto.getUserId());
        OrderItem orderItem = this.orderItemRepository
                .findOrderItemByOrderIdAndSizeIdAndIsDeletedFalseAndProductId(order.getId(), dto.getSizeId(), dto.getProductId())
                .orElse(null);
        if(orderItem == null) {
            return this.orderItemRepository.save(new OrderItem(order, dto.getProductId(), dto.getSizeId(), dto.getQuantity()));
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
