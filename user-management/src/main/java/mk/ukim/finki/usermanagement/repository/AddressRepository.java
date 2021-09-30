package mk.ukim.finki.usermanagement.repository;

import mk.ukim.finki.usermanagement.domain.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
