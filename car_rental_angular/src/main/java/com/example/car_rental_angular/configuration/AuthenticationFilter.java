package com.example.car_rental_angular.configuration;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.car_rental_angular.services.jwt.UserService;
import com.example.car_rental_angular.utils.JWTUtil;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

	private final JWTUtil jwtUtil;
	
	private final UserService userService;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		if((StringUtils.isEmpty(authHeader))|| !StringUtils.startsWithIgnoreCase(authHeader,"Bearer")) {
			filterChain.doFilter(request, response);
			return ;
		}
		jwt = authHeader.substring(7);
		userEmail = jwtUtil.extractUserName(jwt);
		if((StringUtils.isEmpty(userEmail) == false)
				&& SecurityContextHolder.getContext().getAuthentication()== null) {
			UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
			if (jwtUtil.isTokenValid(jwt, userDetails)) {
				SecurityContext context = SecurityContextHolder.createEmptyContext();
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken( 
						userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				context.setAuthentication(authToken);
				SecurityContextHolder.setContext(context);
			}
		}
		filterChain.doFilter(request, response);
		
	}

}
