package mk.ukim.finki.usermanagement.security.services;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class CustomTokenConverter extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        OAuth2AccessToken oAuth2AccessToken;
        final Map<String, Object> additionalInfo = new HashMap<>(this.mapUserInfo((UserDetailsImpl) authentication.getPrincipal()));
        //If we want to Include information JWT Token then we used bellow line.
        //((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        oAuth2AccessToken = super.enhance(accessToken, authentication);
        oAuth2AccessToken.getAdditionalInformation().putAll(additionalInfo);
        return oAuth2AccessToken;
    }

    private Map<String, Object> mapUserInfo(UserDetailsImpl details) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", details.getUserId());
        userInfo.put("email", details.getUsername());
        userInfo.put("role", ((ArrayList<GrantedAuthority>) details.getAuthorities()).get(0).getAuthority().toString());
        return userInfo;
    }
}
