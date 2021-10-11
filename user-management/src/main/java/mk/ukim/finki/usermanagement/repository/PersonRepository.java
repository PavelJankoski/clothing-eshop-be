package mk.ukim.finki.usermanagement.repository;

import mk.ukim.finki.usermanagement.domain.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findPersonByEmailAndIsDeletedFalse(String email);
}
