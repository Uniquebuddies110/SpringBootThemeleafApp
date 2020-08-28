package com.ajay.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ajay.api.model.User;

@Service
public class UserService {

	// Store Users
	private List<User> users = new ArrayList<>();
	
	// Sequence
	volatile Integer id=1;
	
	public List<User> getAllUsers(){
		return users;
	}
	
	public User getUserById(Integer id){
		User existUser=null;
		for(User user:users)
		{
			if(user.getId()==id)
				existUser=user;
		}
		return existUser;
	}
	
	public String addUser(User user){
		user.setId(id);
		users.add(user);
		id++;
		System.out.println(user);
		return "User '"+user.getName()+"' has been added";
	}
}
