package com.example.car_rental_angular.services.auth;

import com.example.car_rental_angular.dto.SignupRequest;
import com.example.car_rental_angular.dto.UserDto;

public interface AuthService {
	UserDto createCustomer(SignupRequest signupRequest);

	boolean hasCustomerWithEmail(String email);
}
