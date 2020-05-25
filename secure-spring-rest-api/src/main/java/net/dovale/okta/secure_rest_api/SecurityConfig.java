package net.dovale.okta.secure_rest_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity( debug = true )
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public UserDetailsService userDetailsService() {
	    return super.userDetailsService();
	}
//	@Autowired
//    private PasswordEncoder passwordEncoder;
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder( passwordEncoder );
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }
	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;
	
    @Autowired
    public UserDetailsService userDetailsService;

    

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService);
//    			  .passwordEncoder(passwordEncoder);
//    }
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.parentAuthenticationManager(authenticationManager)
			.inMemoryAuthentication()
//			.passwordEncoder(passwordEncoder)
			.withUser("arora.sagar14794@gmail.com")
			.password("{noop}#Kingston1")
			.roles("USER")
			.and()
			.withUser("peter")
			.password("{noop}peter")
			.roles("USER");
	}
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .antMatchers("/","/**").permitAll()
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .and().httpBasic().and()
//                .csrf().disable();
    	http
//    	.authorizeRequests().anyRequest().authenticated()
//        .and()
//        .oauth2ResourceServer().jwt()
//        .and()
    	.httpBasic().disable().formLogin().permitAll().and().requestMatchers()
        .antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access").and().authorizeRequests()
        .anyRequest().authenticated();
    }
}

