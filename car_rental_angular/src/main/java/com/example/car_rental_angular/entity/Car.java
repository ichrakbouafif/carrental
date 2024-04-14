package com.example.car_rental_angular.entity;

import java.util.Date;

import com.example.car_rental_angular.dto.CarDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="cars")
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String brand;
	private String color;
	private String name;
	private String type ;
	private String transmission;
	private String description;
	private long price;
	private Date year;
	
	@Column(columnDefinition = "longblob")
	private byte[] image;
	
	public CarDto getCarDto() {
		CarDto carDto = new CarDto();
		carDto.setId(id);
		carDto.setName(name);
		carDto.setBrand(brand);
		carDto.setColor(color);
		carDto.setPrice(price);
		carDto.setDescription(description);
		carDto.setTransmission(transmission);
		carDto.setType(type);
		carDto.setYear(year);
		carDto.setReturnedImage(image);
		
		return carDto;
	}

}
