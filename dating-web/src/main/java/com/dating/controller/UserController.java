package com.dating.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dating.domain.UserExample;
import com.dating.domain.UserWithBLOBs;
import com.dating.service.UserService;

/**
 * @author elvis
 */
@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/users")
	@ResponseBody
	public Object getUsers() {
		UserExample example = new UserExample();
		example.createCriteria().andIsDeleteIsNull();
		List<UserWithBLOBs> users = userService.selectByExampleWithBLOBs(example);
		if (users == null) {

		}
		return users;
	}
}
