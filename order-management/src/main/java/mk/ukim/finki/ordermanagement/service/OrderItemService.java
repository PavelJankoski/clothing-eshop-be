package mk.ukim.finki.ordermanagement.service;

import mk.ukim.finki.ordermanagement.domain.dtos.request.AddProductToOrderDto;
import mk.ukim.finki.ordermanagement.domain.dtos.request.RemoveProductFromOrderDto;
import mk.ukim.finki.ordermanagement.domain.models.OrderItem;

public interface OrderItemService {
    void addProductToOrder(AddProductToOrderDto dto);

    void removeProductFromOrder(RemoveProductFromOrderDto dto);
}
