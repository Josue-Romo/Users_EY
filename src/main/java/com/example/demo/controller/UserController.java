package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping()
	public ArrayList<User> getUser() {
		return userService.getUser();
	}

	// CREATE A USER
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		HashMap<Object, Object> responseJson = new HashMap<>();
		int validateEmail = emailValidate(user.getEmail());

		if (validateEmail == 1) {
			responseJson.put("message", "El correo no posee un formato correcto");
			return new ResponseEntity<HashMap>(responseJson, HttpStatus.BAD_REQUEST);
		} else if (validateEmail == 2) {
			responseJson.put("message", "El correo ya registrado");
			return new ResponseEntity<HashMap>(responseJson, HttpStatus.BAD_REQUEST);
		} else if (!pwdValidate(user.getPassword())) {
			responseJson.put("message", "La pasword no tiene el formato correcto");
			return new ResponseEntity<HashMap>(responseJson, HttpStatus.BAD_REQUEST);
		} else {
			return ResponseEntity.ok(userService.saveUser(user));
		}

	}

	public boolean pwdValidate(String password) {
		String regx = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();

	}

	public int emailValidate(String email) {
		int response = 0;
		ArrayList<User> users = userService.getUser();

		String regx = "^[A-Za-z0-9+_.-]+@(.+)$";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches())
			response = 1;
		for (User us : users) {
			if (us.getEmail().contains(email))
				response = 2;
		}
		return response;

	}
}
