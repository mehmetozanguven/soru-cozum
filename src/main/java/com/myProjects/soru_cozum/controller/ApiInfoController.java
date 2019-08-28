package com.myProjects.soru_cozum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiInfoController {
	@GetMapping("/info")
	public String hello() {
		return "apiInfo";
	}
}
