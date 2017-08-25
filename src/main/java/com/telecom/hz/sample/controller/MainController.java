package com.telecom.hz.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	@RequestMapping(value= {"", "/index"}, method=RequestMethod.GET)
	public String root() {
		
		return "index";
	}
	
	@RequestMapping(value= "/login", method=RequestMethod.GET)
	public String login() {
		
		return "login";
	}
	
	@RequestMapping(value= "/upload", method=RequestMethod.GET)
	public String upload() {
		
		return "upload";
	}
}
