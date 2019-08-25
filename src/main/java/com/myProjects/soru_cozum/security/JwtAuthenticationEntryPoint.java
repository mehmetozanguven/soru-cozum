package com.myProjects.soru_cozum.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/***
 * This class is responsible for error in the request(s)
 * It will set the response as 401 - unauthorized access
 * @author mehmetozanguven
 *
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
	private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		LOGGER.debug("Request is: " + request);
		LOGGER.debug("Request's header is: " + request.getHeader("Authorization"));
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied");
	}
}
