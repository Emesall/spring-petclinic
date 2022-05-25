package com.emesall.petclinic.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.emesall.petclinic.exceptions.NotFoundException;

@ControllerAdvice
public class ExceptionHandlingController {

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ModelAndView handleNotFound(Exception exception) {
		String NOT_FOUND = "Oops!";
		ModelAndView mav = new ModelAndView();
		mav.addObject("response", NOT_FOUND);
		mav.addObject("message", "Not Found");
		mav.setViewName("errorPage");
		return mav;
	}
	
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handleNotFoundUrl(Exception exception) {
		String NOT_FOUND = "Oops!";
		System.out.println("not found handler");
		ModelAndView mav = new ModelAndView();
		mav.addObject("response", NOT_FOUND);
		mav.addObject("message", "Page not found");
		mav.setViewName("errorPage");
		return mav;
	}
}
