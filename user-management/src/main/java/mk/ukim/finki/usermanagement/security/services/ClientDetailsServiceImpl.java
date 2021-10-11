package mk.ukim.finki.usermanagement.security.services;

import mk.ukim.finki.usermanagement.domain.models.OauthClient;
import mk.ukim.finki.usermanagement.repository.OauthClientRepository;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final OauthClientRepository repository;

    public ClientDetailsServiceImpl(OauthClientRepository repository) {
        this.repository = repository;
    }


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        OauthClient c=repository.findOauthClientByClientId(clientId).orElseThrow(() -> new ClientRegistrationException("client with "+clientId +" is not available"));


        return new ClientDetailsImpl(c);
    }

}