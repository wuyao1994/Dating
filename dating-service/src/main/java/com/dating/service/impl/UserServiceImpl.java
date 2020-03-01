package com.dating.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dating.annotation.BaseService;
import com.dating.dao.UserMapper;
import com.dating.domain.User;
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
		example.createCriteria().andUsernameEqualTo(user.getUsername());
		final long alreadyExistUser = userMapper.countByExample(example);
		if (alreadyExistUser > 0) {
			return null;
		}
		userMapper.insert(user);
		return user;
	}

	@Override
	public User getCurrentUser(String username) {
		UserExample example = new UserExample();
		example.createCriteria().andUsernameEqualTo(username).andIsDeleteIsNull();
		List<User> user = userMapper.selectByExample(example);
		return user.get(0);
	}

	@Override
	public Integer deleteCurrentUser(int id) {
		UserWithBLOBs user = new UserWithBLOBs();
		user.setIsDelete(1);
		user.setId(id);
		int count = userMapper.updateByPrimaryKeySelective(user);
		return null;
	}
}
