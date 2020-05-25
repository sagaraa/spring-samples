package net.dovale.okta.secure_rest_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	private String resourceId = "oauth2_application";
	private int accessTokenValiditySeconds=3600;
	private int refreshTokenValiditySeconds=10000;	
	@Autowired
	private AuthenticationManager authenticationManager;

//	@Override
//	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//		security.tokenKeyAccess("permitAll()")
//				.checkTokenAccess("isAuthenticated()");
//	}
	//no change removing above code
	@Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("isAnonymous() || hasRole('USER')")
                .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
    }

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.inMemory()
//			   .withClient("clientId")
//			   .secret("{noop}secret")
//			   .authorizedGrantTypes("authorization_code")
//			   .scopes("user_info")
//			   .autoApprove(true);
		clients.inMemory()
		.withClient("0oacthfyiMpaDPv4G356").secret("{noop}gt9d5FAosXhE4YlkuwZOnHQxOTJXjoXVsAGQI-ad")
        .authorizedGrantTypes("authorization_code", "refresh_token", "password").scopes("openid")
        .autoApprove(true);
	}
//	@Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("normal-app")
//                    .authorizedGrantTypes("authorization_code", "implicit")
//                    .authorities("ROLE_CLIENT")
//                    .scopes("read", "write")
//                    .resourceIds(resourceId)
//                    .secret("secret")
//                    .accessTokenValiditySeconds(accessTokenValiditySeconds)
//                    .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
//                    .and()
//                .withClient("trusted-app")
//                    .authorizedGrantTypes("client_credentials", "password", "refresh_token")
//                    .authorities("ROLE_TRUSTED_CLIENT")
//                    .scopes("read", "write")
//                    .resourceIds(resourceId)
//                    .accessTokenValiditySeconds(accessTokenValiditySeconds)
//                    .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
//                    .secret("secret")
//                    .and()
//                .withClient("register-app")
//                    .authorizedGrantTypes("client_credentials")
//                    .authorities("ROLE_REGISTER")
//                    .scopes("read")
//                    .resourceIds(resourceId)
//                    .secret("secret")
//                .and()
//                    .withClient("my-client-with-registered-redirect")
//                    .authorizedGrantTypes("authorization_code")
//                    .authorities("ROLE_CLIENT")
//                    .scopes("read", "trust")
//                    .resourceIds("oauth2-resource")
//                    .redirectUris("http://anywhere?key=value");
//    }

//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		endpoints.authenticationManager(authenticationManager);
//	}
	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(this.authenticationManager)
                .tokenServices(tokenServices())
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter());
    }
	
	
	
	@Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
	
  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
      JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//      try {
//          converter.setSigningKey(keyProvider.getKey());
//      } catch (URISyntaxException | KeyStoreException | NoSuchAlgorithmException | IOException | UnrecoverableKeyException | CertificateException e) {
//          e.printStackTrace();
//      }

      return converter;
  }
	
	@Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
        return defaultTokenServices;
    }
	

}
