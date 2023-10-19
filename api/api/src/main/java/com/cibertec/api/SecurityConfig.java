package com.cibertec.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@SuppressWarnings("deprecation")
@EnableWebSecurity
@Configuration
public class SecurityConfig {
	//metodo para encriptar y el Bean es para poder usar en otro lado(clase) el metodo BCryptPasswordEncoder 
	//con esto podremos encriptar el password
	@Bean 
	public BCryptPasswordEncoder passwordEncoder() { return new
	BCryptPasswordEncoder(); 
	}

	// autenticacion al form login
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		/*http.authorizeHttpRequests((auth)-> auth.anyRequest().authenticated())
		.formLogin(form-> form.loginPage("/login")
		.permitAll().defaultSuccessUrl("/listar"));*/
		
	http.csrf(csrf->csrf.disable())
	//.authorizeHttpRequests((auth)-> {auth.antMatchers("/lista").hasRole("Administrador");
		.authorizeHttpRequests((auth)-> {auth.requestMatchers("/lista").hasRole("Administrador"); 
		auth.anyRequest().authenticated();})
		.formLogin(form-> form.loginPage("/login")
		.permitAll().defaultSuccessUrl("/intranet"));
	
	http.exceptionHandling(exceptions -> {
	    exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
	    exceptions.accessDeniedPage("/intranet");
	});
	return http.build();
	}
}
