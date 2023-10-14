package com.cibertec.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// autenticacion al form login
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		/*http.authorizeHttpRequests((auth)-> auth.anyRequest().authenticated())
		.formLogin(form-> form.loginPage("/login")
		.permitAll().defaultSuccessUrl("/listar"));*/
		
		http.csrf(csrf->csrf.disable())
		.authorizeHttpRequests((auth)-> auth.anyRequest().authenticated())
		.formLogin(form-> form.loginPage("/login")
		.permitAll().defaultSuccessUrl("/intranet"));
		
		return http.build();
		}
}
