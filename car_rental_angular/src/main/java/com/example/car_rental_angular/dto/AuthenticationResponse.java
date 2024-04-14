package com.example.car_rental_angular.dto;

import com.example.car_rental_angular.enums.UserRole;

import lombok.Data;

@Data
public class AuthenticationResponse {
	private String jwt;
	private UserRole userRole;
	private long UserId;
}
