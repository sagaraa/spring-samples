package com.okta.okta.spring.example;

import java.util.Collections;

import javax.servlet.Filter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.okta.okta.spring.example.controller.CustomAccessDeniedHandler;
import com.okta.okta.spring.example.interceptor.RequestResponseLoggingInterceptor;
import com.okta.spring.config.OktaOAuth2Properties;

@SpringBootApplication
@EnableOAuth2Sso
public class OktaSpringSecurityRolesExampleApplication {

    private final OktaOAuth2Properties oktaOAuth2Properties;
   // private AccessDeniedHandler customAccessDeniedHandler;


    public OktaSpringSecurityRolesExampleApplication(OktaOAuth2Properties oktaOAuth2Properties) {
        this.oktaOAuth2Properties = oktaOAuth2Properties;
    }

    @EnableGlobalMethodSecurity(prePostEnabled = true)
    protected static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {
        @Override
        protected MethodSecurityExpressionHandler createExpressionHandler() {
            return new OAuth2MethodSecurityExpressionHandler();
        }
    }

    @Bean
    protected Filter oktaSsoFilter(
        ApplicationEventPublisher applicationEventPublisher,
        OAuth2ClientContext oauth2ClientContext,
        AuthorizationCodeResourceDetails authorizationCodeResourceDetails,
        ResourceServerTokenServices tokenServices
    ) {
        OAuth2ClientAuthenticationProcessingFilter oktaFilter =
            new OAuth2ClientAuthenticationProcessingFilter(oktaOAuth2Properties.getRedirectUri());
        oktaFilter.setApplicationEventPublisher(applicationEventPublisher);

        OAuth2RestTemplate oktaTemplate = new OAuth2RestTemplate(authorizationCodeResourceDetails, oauth2ClientContext);
        setupTemplateLogging(oktaTemplate);

        oktaFilter.setRestTemplate(oktaTemplate);
        oktaFilter.setTokenServices(tokenServices);
        return oktaFilter;
    }

    private void setupTemplateLogging(OAuth2RestTemplate oktaTemplate) {
        ClientHttpRequestFactory requestFactory =
            new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        AuthorizationCodeAccessTokenProvider authCodeAccessTokenProvider = new AuthorizationCodeAccessTokenProvider();
        authCodeAccessTokenProvider.setRequestFactory(requestFactory);
        authCodeAccessTokenProvider.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
        AccessTokenProvider accessTokenProvider =
            new AccessTokenProviderChain(Collections.singletonList(authCodeAccessTokenProvider));
        oktaTemplate.setAccessTokenProvider(accessTokenProvider);
    }

    @Configuration
    @Order(99)
    static class OAuth2SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        private final Filter oktaSsoFilter;
        private final OktaOAuth2Properties oktaOAuth2Properties;
        private final CustomAccessDeniedHandler customAccessDeniedHandler;

        OAuth2SecurityConfigurerAdapter(
            Filter oktaSsoFilter,
            OktaOAuth2Properties oktaOAuth2Properties,
            CustomAccessDeniedHandler customAccessDeniedHandler
        ) {
            this.oktaSsoFilter = oktaSsoFilter;
            this.oktaOAuth2Properties = oktaOAuth2Properties;
            this.customAccessDeniedHandler = customAccessDeniedHandler;
        }
        
        private String findCookie(String string, Cookie[] cookies) {
    		for(int i=0; i<cookies.length; i++) {
    			if(string == cookies[i].getName())
//    				return cookies[i].getValue();
    			System.out.println(cookies[i].getName());
    		}
    		return "jhbc";
    	}

        @Bean
        protected AuthenticationEntryPoint authenticationEntryPoint() {
            return new LoginUrlAuthenticationEntryPoint(oktaOAuth2Properties.getRedirectUri());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .addFilterAfter(oktaSsoFilter, AbstractPreAuthenticatedProcessingFilter.class)
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .accessDeniedHandler(customAccessDeniedHandler)
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/login", "/images/**", "/favicon.ico","/oktacredentials").permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/");
        }
        
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//			http.authorizeRequests()
//                .antMatchers("/", "/login", "/images/**").permitAll()
//                .and()
//                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
//        }
        
        
        @Bean
        protected ResourceServerConfigurerAdapter resourceServerConfigurerAdapter() {
            return new ResourceServerConfigurerAdapter() {
                @Override
                public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
                    resources.tokenExtractor(new TokenExtractor() {

                        @Override
                        public Authentication extract(HttpServletRequest request) {
                            String tokenValue = findCookie("access_token=", request.getCookies());
                            if (tokenValue == null) { return null; }

                            return new PreAuthenticatedAuthenticationToken(tokenValue, "");
                        }
                     
    					
                    });
                }
            };
        }
    }
    
    public static void main(String[] args) {
		SpringApplication.run(OktaSpringSecurityRolesExampleApplication.class, args);
	}
}
