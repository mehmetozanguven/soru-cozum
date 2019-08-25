package com.myProjects.soru_cozum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myProjects.soru_cozum.request.LoginUserRequest;
import com.myProjects.soru_cozum.response.JwtAuthenticationResponse;
import com.myProjects.soru_cozum.security.JwtTokenProvider;
import com.myProjects.soru_cozum.security.SecurePerson;

@RestController
@RequestMapping("/login")
public class LoginController {
	private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@PostMapping
	public ResponseEntity<?> logInUser(@RequestBody LoginUserRequest loginRequest){
		LOGGER.debug("Login Request");
		String username = loginRequest.getLoginType() + "," +  loginRequest.getUsername();
		UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword());
		LOGGER.debug("Usernamepassword...Token: " + user.getName());
		
		Authentication authentication = authenticationManager.authenticate(user);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		SecurePerson userPrincipal = (SecurePerson) authentication.getPrincipal();
		String jwt = tokenProvider.generateToken(authentication, loginRequest.getLoginType());
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, userPrincipal));
	}
}
