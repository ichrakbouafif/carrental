package com.example.car_rental_angular.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CarDto {
	private long id;
	private String brand;
	private String color;
	private String name;
	private String type;
	private String transmission;
	private String description;
	private long price;
	private Date year;
	private MultipartFile image;
	private byte[] returnedImage;
}
