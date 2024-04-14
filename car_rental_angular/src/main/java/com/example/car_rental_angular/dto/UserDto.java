package com.example.car_rental_angular.dto;

import com.example.car_rental_angular.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {
	private long id;
	private String name;
	private String email;
	private UserRole userRole;
}
