package com.emesall.petclinic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	// login page and index page controller
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/login");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**", "/resources/images/**", "/resources/css/**","/resources/fonts/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/", "classpath:/static/resources/images/",
						"classpath:/static/resources/css/","classpath:/static/resources/fonts/").resourceChain(false);

	}
}