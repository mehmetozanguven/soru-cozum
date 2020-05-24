package com.myProjects.soru_cozum.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * This class is responsible to filter each request
 * </br>
 * In each request, doFilterInternal will run.
 * </br>
 * Then it will check the request's header (looking for Authorization header)
 * </br>
 * If it is find the correspond header it will do some operation,
 * </br>
 * else it will pass the request to the next chain(next filter)
 * @author mehmetozanguven
 *
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	/**
	 * Check the request has a header called authorization
	 * </br>
	 * If it has get the value of that header, if value is null, pass the request to the next chain(request)
	 * <ol>
	 * 	<li> Get the token(jwt) from request's header (Bearer token) </li>
	 * 	<li> If token not empty and its structure matches with io.jsonwebtoken package, then: </li>
	 * 	<ul> 
	 * 		<li> Get user Id from token(claims) </li>
	 * 		<li> Load student by user Id (Go to dao, and findStudentById)</li>
	 * 		<li> Set the UsernamePasswordAuthenticationToken(authentication) with that user (which implements UserDetails interface or created from that interface)</li>
	 * 		<li> Set details of authentication </li>
	 * 		<li> Finally, Spring Security needs to know the who was being authenticated user
	 * 			That's why we have setAuthentication in SecurityContext </li>
	 * 		<li> After all we deployed authenticated user to spring security context </li>
	 * 	</ul>
	 * 	<li> if not, pass the request to the next chain </li>
	 * </ol>
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		LOGGER.debug("Request is: " + request);
		LOGGER.debug("Request's header is: " + request.getHeader("Authorization"));
		try {
			String jwt = getJwtFromRequest(request);
			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				String[] tokenClaim = tokenProvider.getUserIdFromJWT(jwt);
				LOGGER.debug("UserId: " + tokenClaim[1] + " loginType: " + tokenClaim[0]);
                UserDetails userDetails = userDetailsService.loadUserById(tokenClaim[0], Long.parseLong(tokenClaim[1]));
                LOGGER.debug("Token creating");
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}catch (Exception e) {
			LOGGER.error("Could not set user authentication in security context", e);
		}
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        filterChain.doFilter(request, response);
	}
	
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
	}
	
}
