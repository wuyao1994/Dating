package com.dating.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dating.domain.JwtUser;
import com.dating.domain.User;
import com.dating.domain.UserExample;
import com.dating.service.UserService;

/**
 * @author elvis
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserService userService;

	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andEmailEqualTo(name);
		User user = userService.selectFirstByExampleWithBLOBs(example);
		return new JwtUser(user);
	}

}
