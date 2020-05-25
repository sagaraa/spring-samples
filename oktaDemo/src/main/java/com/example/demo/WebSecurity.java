package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(99)
class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/home").permitAll()
		.antMatchers("/random").permitAll()
		.antMatchers("/user").hasAuthority("customer")   	//.hasAuthority("hasRole('customer')")
		.antMatchers("/admin").hasAuthority("admin")
            .anyRequest().authenticated()
        .and()
        .oauth2ResourceServer().jwt();
    }
}

//
//import java.util.Arrays;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
////import org.springframework.context.annotation.Bean;
////import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
////import org.springframework.web.cors.CorsConfiguration;
////import org.springframework.web.cors.CorsConfigurationSource;
////import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//@EnableWebSecurity
//@Configuration
//@Order(99)
//public class WebSecurity extends WebSecurityConfigurerAdapter {
// 
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//        			.antMatchers("/home").permitAll()
//        			.antMatchers("/random").permitAll()
//        			.antMatchers("/user").hasRole("customer")
//        			.antMatchers("/admin").access("hasRole('admin')")
//        			.anyRequest().authenticated()
//        	.and().formLogin();
////        .cors();    
//    }
// 
////    @Bean
////    CorsConfigurationSource corsConfigurationSource()
////    {
////        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
////        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", configuration);
////        return source;
////    }
//}