package mk.ukim.finki.ordermanagement.repository;

import mk.ukim.finki.ordermanagement.domain.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
