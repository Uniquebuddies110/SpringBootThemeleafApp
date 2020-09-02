package com.ajay.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ajay.api.model.User;

@Service
public class UserService {

	// Store Users and initiate values
//	private List<User> users = List.of(new User(1,"Ajay","STUDENT"),new User(2,"Vijay","TEACHER"),new User(3,"Keshav","STUDENT"));
	private List<User> users;

	public UserService() {
		users = new ArrayList<>();
		users.add(new User(1, "Ajay", "STUDENT"));
		users.add(new User(2, "Vijay", "TEACHER"));
		users.add(new User(3, "Keshav", "STUDENT"));
	}

	// Sequence
	volatile Integer id = 4;

	public List<User> getAllUsers() {
		return users;
	}

	public User getUserById(Integer id) {
		User existUser = null;
		for (User user : users) {
			if (user.getId() == id)
				existUser = user;
		}
		return existUser;
	}

	public String addUser(User user) {
		user.setId(id);
		users.add(user);
		id++;
		System.out.println(user);
		return "User '" + user.getName() + "' has been added";
	}
}
