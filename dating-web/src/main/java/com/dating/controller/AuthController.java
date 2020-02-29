package com.dating.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dating.base.UserResult;
import com.dating.constants.UserResultConstant;
import com.dating.domain.UserWithBLOBs;
import com.dating.service.UserService;
import com.dating.util.MD5Util;

/**
 * @author elvis
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	UserService userService;

	@PostMapping("/register")
	@ResponseBody
	public Object registrerUser(@RequestBody UserWithBLOBs user) {
        user.setPassword(MD5Util.encode(user.getPassword()));
		UserWithBLOBs result = userService.createUser(user);
		if (result == null) {
			return new UserResult(UserResultConstant.FAILED, "account is already exist");
		}
		return result;
	}
}
