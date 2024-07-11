package com.security.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankController {
	
	@GetMapping("/account")
	public String account() {
		
		return "Hi welcome to the your account";
	}
	@GetMapping("/balance")
	public String balance() {
		
		return "Your balance is = "+10000;
		
	}

	
	  @GetMapping("/update") public String update() { 
		  return "we have an update for you";
	  
	  }
	 
	@GetMapping("/mainpage")
	public String mainPage() {
		
		return "This is the main page";
	}

}
