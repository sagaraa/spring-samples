package net.dovale.okta.secure_rest_api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;

@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
    @Autowired
    private DefaultTokenServices tokenServices;

    @Autowired
    private TokenStore tokenStore;
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .tokenServices(tokenServices)
                .tokenStore(tokenStore);
    }
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/login").hasAnyRole("USER","ADMIN")
                .antMatchers("/**").access("hasRole('USER')");
//    			.and()   ,"/oauth/authorize"
//    			.formLogin()
//    			.permitAll();
    }
    
//    private static class OAuthRequestedMatcher implements RequestMatcher {
//        public boolean matches(HttpServletRequest request) {
//            String auth = request.getHeader("Authorization");
//            boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
//            boolean haveAccessToken = request.getParameter("access_token")!=null;
//            return haveOauth2Token || haveAccessToken;
//        }
//    }	
}
