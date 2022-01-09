package mk.ukim.finki.ordermanagement.repository;

import mk.ukim.finki.ordermanagement.domain.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findOrderItemByOrderIdAndSizeIdAndIsDeletedFalseAndProductId(Long orderId, Long sizeId, Long productId);
}
