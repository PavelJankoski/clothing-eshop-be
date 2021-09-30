package mk.ukim.finki.usermanagement.repository;

import mk.ukim.finki.usermanagement.domain.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
