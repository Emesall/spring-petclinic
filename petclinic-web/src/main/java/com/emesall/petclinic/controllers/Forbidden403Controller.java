package com.emesall.petclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Forbidden403Controller {

	@GetMapping("/403")
	public ModelAndView handle403Status() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("message", "You have no permission to access this page");
		mav.setViewName("errorPage");
		return mav;
	}
	

}
