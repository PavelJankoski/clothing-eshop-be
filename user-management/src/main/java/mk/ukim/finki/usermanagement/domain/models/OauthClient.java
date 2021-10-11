package mk.ukim.finki.usermanagement.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "oauth_client")
public class OauthClient {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="client_id")
    private String clientId;

    @Column(name="client_secret")
    private String clientSecret;

    @Column(name="access_token_validity")
    private int accessTokenValidity;

    @Column(name="scope")
    private String scope;

    @Column(name="authorities")
    private String authorities;

    @Column(name="authorized_grant_types")
    private String authorizedGrantTypes;

    @Column(name="refresh_token_validity")
    private int refreshTokenValidity;

    @Column(name="resource_ids")
    private String resourceIds;

    @Column(name="web_server_redirect_uri")
    private String webServerRedirectUri;

    @Column(name="autoapprove")
    private String autoApprove;

    @Column(name="additional_information")
    private String addInfo;

    public OauthClient(String clientId, String clientSecret, int accessTokenValidity, String scope, String authorities,
                     String authorizedGrantTypes, int refreshTokenValidity, String resourceIds) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenValidity = accessTokenValidity;
        this.scope = scope;
        this.authorities = authorities;
        this.authorizedGrantTypes = authorizedGrantTypes;
        this.refreshTokenValidity = refreshTokenValidity;
        this.resourceIds=resourceIds;
    }

    public OauthClient(String clientId, String clientSecret, int accessTokenValidity, String scope, String authorities,
                     String authorizedGrantTypes, int refreshTokenValidity, String resourceIds,String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenValidity = accessTokenValidity;
        this.scope = scope;
        this.authorities = authorities;
        this.authorizedGrantTypes = authorizedGrantTypes;
        this.refreshTokenValidity = refreshTokenValidity;
        this.resourceIds=resourceIds;
        this.webServerRedirectUri=redirectUri;
    }
}
