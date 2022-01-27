package com.emesall.petclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/owners") //prefix, added to every each of mapping presented below f.e /owners/index 
@Controller
public class OwnerController {

	@RequestMapping({"","/","/index","/index.html"})
	public String listOwners()
	{
		return "owners/index";
	}
}
