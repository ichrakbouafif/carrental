package com.example.car_rental_angular.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {

	private String email;
	private String password;
}
