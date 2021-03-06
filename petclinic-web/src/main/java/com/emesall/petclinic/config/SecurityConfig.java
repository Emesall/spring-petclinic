package com.emesall.petclinic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService detailsService;
	private AuthenticationSuccessHandler successHandler;
	

	@Autowired
	public SecurityConfig(@Qualifier("jpaPersonService") UserDetailsService detailsService,
			AuthenticationSuccessHandler successHandler) {
		super();
		this.detailsService = detailsService;
		this.successHandler = successHandler;
		
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();

	}


	@Bean
	public CustomAccessDeniedHandler deniedHandler() {

		return new CustomAccessDeniedHandler();

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
				.antMatchers("/admin/**")
				.hasRole("ADMIN")
				.antMatchers("/owners/**")
				.hasRole("OWNER")
				.antMatchers("/vets/**")
				.hasAnyRole("VET", "OWNER")
				.antMatchers("/pets/**")
				.hasAnyRole("ADMIN", "OWNER")
				.and()
				.exceptionHandling()
				.accessDeniedHandler(deniedHandler())
				.and()
				.formLogin()
				.loginPage("/login")
				.successHandler(successHandler)
				// .defaultSuccessUrl("/",true)
				.and()
				.logout()
				.deleteCookies("JSESSIONID")

				.and()
				.rememberMe()
				.key("uniqueAndSecret");

	}

}
