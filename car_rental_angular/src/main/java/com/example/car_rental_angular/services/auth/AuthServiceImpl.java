package com.example.car_rental_angular.services.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.car_rental_angular.dto.SignupRequest;
import com.example.car_rental_angular.dto.UserDto;
import com.example.car_rental_angular.entity.User;
import com.example.car_rental_angular.enums.UserRole;
import com.example.car_rental_angular.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final UserRepository userRepository;

	@PostConstruct
	public void createAdminAccount() {
		User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
		if (adminAccount == null) {
			User newAdminAccount = new User();
			newAdminAccount.setName("admin");
			newAdminAccount.setEmail("admin@test.com");
			newAdminAccount.setPassword(new BCryptPasswordEncoder().encode("admin"));
			newAdminAccount.setUserRole(UserRole.ADMIN);
			userRepository.save(newAdminAccount);
			System.out.println("Admin account created successfully! ");
		}
	}
	
	@Override
	public UserDto createCustomer(SignupRequest signupRequest) {
		User user = new User();
		user.setName(signupRequest.getName());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setUserRole((UserRole.CUSTOMER));
		User createdUser = userRepository.save(user);
		UserDto userDto = new UserDto();
		userDto.setId(createdUser.getId());
		return userDto;
	}

	@Override
	public boolean hasCustomerWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}
}
