package mk.ukim.finki.usermanagement.repository;

import mk.ukim.finki.usermanagement.domain.models.OauthClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthClientRepository extends JpaRepository<OauthClient, Long> {
    Optional<OauthClient> findOauthClientByClientId(String clientId);
}
