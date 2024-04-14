package com.example.car_rental_angular.controller;

import java.util.Optional;
import com.example.car_rental_angular.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.car_rental_angular.dto.AuthenticationRequest;
import com.example.car_rental_angular.dto.AuthenticationResponse;
import com.example.car_rental_angular.dto.SignupRequest;
import com.example.car_rental_angular.dto.UserDto;
import com.example.car_rental_angular.repository.UserRepository;
import com.example.car_rental_angular.services.auth.AuthService;
import com.example.car_rental_angular.services.jwt.UserService;
import com.example.car_rental_angular.utils.JWTUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final UserRepository userRepository;
	private final AuthenticationManager  authenticationManager;
	private final JWTUtil jwtUtil;
	private final UserService userService;
    private final AuthService authService;
    
    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest) {
        if(authService.hasCustomerWithEmail(signupRequest.getEmail()))
        	return new ResponseEntity<>("Customer already exist with this email",HttpStatus.NOT_ACCEPTABLE);
    	UserDto createdCustomerDto = authService.createCustomer(signupRequest);
        if (createdCustomerDto == null) {
            return new ResponseEntity<>("Customer not created, please try again later", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws 
    		BadCredentialsException,
            DisabledException,
            UsernameNotFoundException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            		authenticationRequest.getEmail(),
            		authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password.");
        }

        
		final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional <User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());

        final String jwt = jwtUtil.generatedToken(userDetails);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }

        return authenticationResponse;
    }

    
    
}
