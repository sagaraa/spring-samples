package net.dovale.okta.secure_rest_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SecureRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureRestApiApplication.class, args);
	}
	
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder(){
//		return new BCryptPasswordEncoder();
//	}
	
}
