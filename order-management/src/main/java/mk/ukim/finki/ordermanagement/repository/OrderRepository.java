package mk.ukim.finki.ordermanagement.repository;

import mk.ukim.finki.ordermanagement.domain.enums.OrderStatus;
import mk.ukim.finki.ordermanagement.domain.models.Order;
import mk.ukim.finki.ordermanagement.domain.projections.GetProductExistsInShoppingCart;
import mk.ukim.finki.ordermanagement.util.QueryConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = QueryConstants.isProductInShoppingCart, nativeQuery = true)
    GetProductExistsInShoppingCart isProductInShoppingCart(Long userId, Long productId);

    Optional<Order> findOrderByUserIdAndStatusAndIsDeletedFalse(Long userId, OrderStatus status);

    List<Order> findAllByIsDeletedFalseAndStatus(OrderStatus status);

    List<Order> findAllByUserIdAndStatusAndIsDeletedFalseOrderByModifiedOnDesc(Long userId, OrderStatus status);
}
