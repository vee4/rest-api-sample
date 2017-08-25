package com.telecom.hz.sample.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.telecom.hz.sample.domain.User;

@RestController
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
//	@RequestMapping(value="/api/v1/auth/login", method= {RequestMethod.POST, RequestMethod.OPTIONS},
//			produces= "application/json; charset=UTF-8")
//	public User auth(@RequestBody() User user, HttpServletResponse resp) {
//		logger.info(user.toString());
//		
//		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//		
//		
//		return user;
//	}
}
