package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;

@Service
public class UserService {
	@Autowired
	UserRepo userRepo;
	
	public ArrayList<User> getUser()
	{
		return (ArrayList<User>)userRepo.findAll();
	}
	
	public User saveUser(User user) {
		
		return userRepo.save(user);
	}
	

}
