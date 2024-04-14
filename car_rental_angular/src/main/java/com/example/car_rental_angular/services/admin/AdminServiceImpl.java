package com.example.car_rental_angular.services.admin;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.car_rental_angular.dto.CarDto;
import com.example.car_rental_angular.repository.CarRepository;
import com.example.car_rental_angular.entity.Car;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

	private final CarRepository carRepository;

	@Override
	public boolean postCar(CarDto carDto) throws IOException {
		try {
			Car car = new Car();
			car.setName(carDto.getName());
			car.setBrand(carDto.getBrand());
			car.setColor(carDto.getColor());
			car.setPrice(carDto.getPrice());
			car.setYear(carDto.getYear());
			car.setType(carDto.getType());
			car.setDescription(carDto.getDescription());
			car.setTransmission(carDto.getTransmission());
			car.setImage(carDto.getImage().getBytes());
			carRepository.save(car);
			return true;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<CarDto> getAllCars() {
		return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
	}
}
