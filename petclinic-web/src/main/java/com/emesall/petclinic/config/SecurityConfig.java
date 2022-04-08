package com.emesall.petclinic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService detailsService;

	@Autowired
	public SecurityConfig(UserDetailsService detailsService) {
		super();
		this.detailsService = detailsService;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();

	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * auth.inMemoryAuthentication() .withUser("user") .password("{noop}user")
		 * .authorities("USER") .and() .withUser("admin") .password("{noop}admin")
		 * .authorities("ADMIN")''
		 */
		auth.userDetailsService(detailsService).passwordEncoder(encoder());

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
					.antMatchers("/owners/**")
					.hasRole("OWNER")
					.antMatchers("/login/**").permitAll()
					.antMatchers("/", "/**")
					.permitAll()
				.and()
					.formLogin()
					.loginPage("/login")
				.and()
					.logout()
					.logoutSuccessUrl("/login");

	}

}
