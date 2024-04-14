package com.example.car_rental_angular.services.admin;

import java.io.IOException;
import java.util.List;

import com.example.car_rental_angular.dto.CarDto;

public interface AdminService {

	boolean postCar(CarDto carDto) throws IOException;
	
	List<CarDto> getAllCars();
}
