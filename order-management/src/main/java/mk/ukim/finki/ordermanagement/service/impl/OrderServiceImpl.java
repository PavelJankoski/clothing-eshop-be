package mk.ukim.finki.ordermanagement.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.ordermanagement.domain.dtos.response.GetOrderHistoryDetailsDto;
import mk.ukim.finki.ordermanagement.domain.dtos.response.GetOrderHistoryDto;
import mk.ukim.finki.ordermanagement.domain.dtos.response.GetOrderHistoryItemDto;
import mk.ukim.finki.ordermanagement.domain.enums.OrderStatus;
import mk.ukim.finki.ordermanagement.domain.exceptions.OrderNotFoundException;
import mk.ukim.finki.ordermanagement.domain.models.Order;
import mk.ukim.finki.ordermanagement.domain.models.OrderItem;
import mk.ukim.finki.ordermanagement.repository.OrderRepository;
import mk.ukim.finki.ordermanagement.service.OrderService;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetAddressDto;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetOrderItemDto;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetOrderProductDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @Override
    public Order findById(Long id) {
        return this.orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

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
        List<Order> deliveredOrders = this.orderRepository.findAllByUserIdAndStatusAndIsDeletedFalseOrderByModifiedOnDesc(userId, OrderStatus.DELIVERED);
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

    @Override
    public GetOrderHistoryDetailsDto findDetailsForOrder(Long orderId) {
        GetOrderHistoryDetailsDto orderHistoryDetailsDto = new GetOrderHistoryDetailsDto();
        Order order = this.findById(orderId);
        orderHistoryDetailsDto.setOrderId(orderId);
        orderHistoryDetailsDto.setAddressDto(order.getDetails().getAddress());
        orderHistoryDetailsDto.setTotalPrice(order.getDetails().getTotalAmount());
        orderHistoryDetailsDto.setDate(new SimpleDateFormat("dd MMM yyyy").format(order.getModifiedOn()));
        List<GetOrderProductDto> orderProducts = new ArrayList<>();
        order.getOrderItems().forEach(oi -> {
            GetOrderProductDto getOrderProductDto = this.restTemplate
                    .getForObject(String.format("http://PRODUCT-CATALOG-SERVICE/products/get-order-product/%s/sizes/%s/users/%s", oi.getProductId(), oi.getSizeId(), order.getUserId()),
                            GetOrderProductDto.class);
            if(getOrderProductDto != null) {
                getOrderProductDto.setQuantity(oi.getQuantity());
                getOrderProductDto.setPrice(oi.getPrice());
                orderProducts.add(getOrderProductDto);
            }
        });
        orderHistoryDetailsDto.setOrderProducts(orderProducts);
        return orderHistoryDetailsDto;
    }

    @Override
    public List<Order> findAllPendingOrders() {
        return this.orderRepository.findAllByIsDeletedFalseAndStatus(OrderStatus.PENDING);
    }

    private GetOrderItemDto fetchProductDetails(Long productId, Long sizeId) {
        return this.restTemplate
                .getForObject(String.format("http://PRODUCT-CATALOG-SERVICE/products/to-cart-item/%s/%s", productId, sizeId),
                        GetOrderItemDto.class);
    }
}
