package mk.ukim.finki.usermanagement.repository;

import mk.ukim.finki.usermanagement.domain.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByIsDeletedFalseAndUserId(Long userId);
    Optional<Address> findAddressByIdAndIsDeletedFalse(Long id);
}
