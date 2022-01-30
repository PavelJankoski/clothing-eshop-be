package mk.ukim.finki.ordermanagement.service;

import mk.ukim.finki.ordermanagement.domain.dtos.request.AddProductToOrderDto;
import mk.ukim.finki.ordermanagement.domain.dtos.request.ChangeOrderItemQuantityDto;
import mk.ukim.finki.ordermanagement.domain.dtos.request.RemoveProductFromOrderDto;

public interface OrderItemService {
    void addProductToOrder(AddProductToOrderDto dto);

    void removeProductFromOrder(RemoveProductFromOrderDto dto);

    void changeQuantity(ChangeOrderItemQuantityDto dto);
}
