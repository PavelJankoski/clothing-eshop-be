package mk.ukim.finki.ordermanagement.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.ordermanagement.domain.dtos.response.GetOrderDetailsDto;
import mk.ukim.finki.ordermanagement.domain.models.Order;
import mk.ukim.finki.ordermanagement.domain.models.OrderDetails;
import mk.ukim.finki.ordermanagement.domain.models.OrderItem;
import mk.ukim.finki.ordermanagement.domain.valueobjects.Address;
import mk.ukim.finki.ordermanagement.repository.OrderDetailsRepository;
import mk.ukim.finki.ordermanagement.service.OrderDetailsService;
import mk.ukim.finki.ordermanagement.service.OrderService;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetAddressDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {
    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderService orderService;
    private final RestTemplate restTemplate;


    public OrderDetails findOrCreateDetails(Long userId) {
        Order order = this.orderService.findPendingOrderForUser(userId);
        OrderDetails orderDetails;
        orderDetails = new OrderDetails();
        GetAddressDto userDefaultAddress = this.restTemplate
                .getForObject(String.format("http://USERS-API-GATEWAY/addresses/%s/default-address", userId),
                        GetAddressDto.class);
        if (userDefaultAddress != null) {
            Address defaultAddress = new Address(
                    userDefaultAddress.getStreet(),
                    userDefaultAddress.getStreetNo(),
                    userDefaultAddress.getCity(),
                    userDefaultAddress.getCountry(),
                    userDefaultAddress.getPostalCode());
            orderDetails.setAddress(defaultAddress);
        }
        float totalPrice = 0;
        for (OrderItem oi : order.getOrderItems()) {
            totalPrice += oi.getPrice() * oi.getQuantity();
        }
        orderDetails.setTotalAmount(totalPrice);
        OrderDetails orderDetailsResponse = this.orderDetailsRepository.save(orderDetails);
        this.orderService.save(order);
        return orderDetailsResponse;
    }

    @Override
    public GetOrderDetailsDto findOrderDetailsForUser(Long userId) {
        OrderDetails orderDetails = this.findOrCreateDetails(userId);
        GetAddressDto getAddressDto = null;
        if (orderDetails.getAddress() != null) {
            getAddressDto = new GetAddressDto(0L, orderDetails.getAddress().getStreet(),
                    orderDetails.getAddress().getStreetNo(), orderDetails.getAddress().getCity(),
                    orderDetails.getAddress().getCountry(), orderDetails.getAddress().getPostalCode(), true);
        }
        return new GetOrderDetailsDto(getAddressDto, orderDetails.getTotalAmount());
    }
}
