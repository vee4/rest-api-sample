package com.telecom.hz.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
  
@SpringBootApplication 
public class App extends SpringBootServletInitializer{ 
	  
	public static void main(String[] args) {
//		SpringApplication.run(App.class);
		SpringApplication app = new SpringApplication(App.class);
		app.run();
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub

		builder.sources(this.getClass());
		return super.configure(builder);
	}
}
