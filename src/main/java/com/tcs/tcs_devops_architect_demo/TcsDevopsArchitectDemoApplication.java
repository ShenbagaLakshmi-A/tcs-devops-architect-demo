package com.tcs.tcs_devops_architect_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class TcsDevopsArchitectDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TcsDevopsArchitectDemoApplication.class, args);
	}

	
	@GetMapping("/")
	public String home() {
		return "<h1>Welcome to TCS DevOps Architect Demo Application</h1>";
	}
	
	@GetMapping("/about")
	public String about() {
		return "<h1>We are TCS Team, responsible for the DevOps activities</h1>";
	}
}
