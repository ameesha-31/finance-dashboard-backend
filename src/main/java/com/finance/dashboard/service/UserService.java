package com.finance.dashboard.service;

import java.util.List;

import com.finance.dashboard.model.User;
import com.finance.dashboard.repository.UserRepository;

public class UserService {
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
}
