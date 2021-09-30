package mk.ukim.finki.usermanagement.repository;

import mk.ukim.finki.usermanagement.domain.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
