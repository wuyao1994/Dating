package com.dating.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dating.annotation.BaseService;
import com.dating.dao.UserMapper;
import com.dating.domain.UserExample;
import com.dating.domain.UserWithBLOBs;
import com.dating.service.UserService;

/**
 * @author elvis
 */
@Service
@Transactional
@BaseService
public class UserServiceImpl extends
		BaseServiceImpl<UserMapper, UserWithBLOBs, UserExample> implements UserService {
	@Autowired
	UserMapper userMapper;

	@Override
	public UserWithBLOBs createUser(UserWithBLOBs user) {
		UserExample example = new UserExample();
		example.createCriteria().andEmailEqualTo(user.getEmail());
		final long alreadyExistUser = userMapper.countByExample(example);
		if (alreadyExistUser > 0) {
			return null;
		}
		userMapper.insert(user);
		return user;
	}
}
