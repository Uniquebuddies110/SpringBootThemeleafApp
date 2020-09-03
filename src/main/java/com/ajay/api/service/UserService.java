package com.ajay.api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajay.api.model.User;
import com.ajay.api.repo.UserRepository;

@Service
public class UserService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepo;

	public List<User> getAllUsers() {
		LOGGER.info("UserService.getAllUsers()");
		return userRepo.findAll();
	}

	public User getUserById(Integer id) {
		LOGGER.info("UserService.getUserById()");
		Optional<User> optional = userRepo.findById(id);
		if (optional.isPresent())
			return optional.get();
		else
			LOGGER.error("/////--------No Data Found For Id-----------/////////");
			return null;
	}

	public String addUser(User user) {
		LOGGER.info("UserService.addUser()");
		user = userRepo.save(user);
		System.out.println(user);
		return "User '" + user.getName() + "' has been added";
	}
}
