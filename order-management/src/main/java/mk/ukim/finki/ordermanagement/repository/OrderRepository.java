package mk.ukim.finki.ordermanagement.repository;

import mk.ukim.finki.ordermanagement.domain.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
