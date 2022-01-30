package mk.ukim.finki.ordermanagement.service;

import mk.ukim.finki.ordermanagement.domain.dtos.response.GetOrderDetailsDto;
import mk.ukim.finki.ordermanagement.domain.models.OrderDetails;

public interface OrderDetailsService {
    GetOrderDetailsDto findOrderDetailsForUser(Long userId);
}
