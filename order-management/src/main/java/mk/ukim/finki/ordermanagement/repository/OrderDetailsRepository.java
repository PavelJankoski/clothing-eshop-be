package mk.ukim.finki.ordermanagement.repository;

import mk.ukim.finki.ordermanagement.domain.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
