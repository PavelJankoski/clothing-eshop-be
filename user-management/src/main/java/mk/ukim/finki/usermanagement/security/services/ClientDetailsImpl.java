package mk.ukim.finki.usermanagement.security.services;

import mk.ukim.finki.usermanagement.domain.models.OauthClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

public class ClientDetailsImpl implements ClientDetails {


    private static final long serialVersionUID = -5294562011836131914L;

    private final OauthClient client;

    public ClientDetailsImpl(OauthClient client) {
//		System.out.println("inside client details impl" + client.getClientId());
        this.client=client;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {

        return this.client.getAccessTokenValidity();
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {

        return null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> ga=new ArrayList<GrantedAuthority>();
        ga.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
        return ga;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {

        String[]  gt=this.client.getAuthorizedGrantTypes().split(",");

        Set<String> gts=new HashSet<String>();

        Collections.addAll(gts, gt);

//		gts.stream().forEach(System.out::println);

        return gts;
    }

    @Override
    public String getClientId() {

        return this.client.getClientId();
    }

    @Override
    public String getClientSecret() {

        return this.client.getClientSecret();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {

        return this.client.getRefreshTokenValidity();
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {

        String[]  gt=this.client.getWebServerRedirectUri().split(",");

        Set<String> gts=new HashSet<String>();

        Collections.addAll(gts, gt);

        return gts;
    }

    @Override
    public Set<String> getResourceIds() {

        String[]  gt=this.client.getResourceIds().split(",");

        Set<String> gts=new HashSet<String>();

        Collections.addAll(gts, gt);

        return gts;
    }

    @Override
    public Set<String> getScope() {

        String[]  gt=this.client.getScope().split(",");

        Set<String> gts=new HashSet<String>();

        Collections.addAll(gts, gt);

        return gts;
    }

    @Override
    public boolean isAutoApprove(String arg0) {

        return false;
    }

    @Override
    public boolean isScoped() {

        return true;
    }

    @Override
    public boolean isSecretRequired() {

        return true;
    }

}