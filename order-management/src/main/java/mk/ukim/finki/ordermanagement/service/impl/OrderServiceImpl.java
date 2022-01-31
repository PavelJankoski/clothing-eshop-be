package mk.ukim.finki.ordermanagement.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.ordermanagement.domain.dtos.response.GetOrderHistoryDto;
import mk.ukim.finki.ordermanagement.domain.dtos.response.GetOrderHistoryItemDto;
import mk.ukim.finki.ordermanagement.domain.enums.OrderStatus;
import mk.ukim.finki.ordermanagement.domain.models.Order;
import mk.ukim.finki.ordermanagement.domain.models.OrderItem;
import mk.ukim.finki.ordermanagement.repository.OrderRepository;
import mk.ukim.finki.ordermanagement.service.OrderService;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetOrderItemDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
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
            GetOrderItemDto orderItemDto = this.fetchProductDetails(oi.getProductId(), oi.getSizeId());
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

    @Override
    public Integer getItemsInBagNumber(Long userId) {
        Order order = this.findPendingOrderForUser(userId);
        return order.getOrderItems()
                .stream()
                .filter(oi -> !oi.getIsDeleted())
                .map(OrderItem::getQuantity)
                .reduce(0, Integer::sum);
    }

    @Override
    public void save(Order order) {
        this.orderRepository.save(order);
    }

    @Override
    public void placeOrder(Long userId) {
        Order order = this.findPendingOrderForUser(userId);
        order.setStatus(OrderStatus.DELIVERED);
        this.orderRepository.save(order);
    }

    @Override
    public GetOrderHistoryDto findDeliveredOrders(Long userId) {
        GetOrderHistoryDto historyDto = new GetOrderHistoryDto();
        List<GetOrderHistoryItemDto> historyItemDtos = new ArrayList<>();
        List<Order> deliveredOrders = this.orderRepository.findAllByUserIdAndStatusAndIsDeletedFalse(userId, OrderStatus.DELIVERED);
        historyDto.setTotalOrders(deliveredOrders.size());
        deliveredOrders.forEach(o -> {
            GetOrderHistoryItemDto itemDto = new GetOrderHistoryItemDto();
            List<String> imageUrls = new ArrayList<>();
            itemDto.setOrderId(o.getId());
            itemDto.setDeliveredOn(new SimpleDateFormat("dd MMM yyyy").format(o.getModifiedOn()));
            o.getOrderItems().forEach(oi -> {
                GetOrderItemDto orderItemDto = this.fetchProductDetails(oi.getProductId(), oi.getSizeId());
                if(orderItemDto != null) {
                    imageUrls.add(orderItemDto.getImageUrl());
                }
            });
            itemDto.setImageUrls(imageUrls);
            historyItemDtos.add(itemDto);
        });
        historyDto.setOrderHistoryItemDtos(historyItemDtos);
        return historyDto;
    }

    private GetOrderItemDto fetchProductDetails(Long productId, Long sizeId) {
        return this.restTemplate
                .getForObject(String.format("http://PRODUCT-CATALOG-SERVICE/products/to-cart-item/%s/%s", productId, sizeId),
                        GetOrderItemDto.class);
    }
}
