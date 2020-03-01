package com.dating.service;

import com.dating.domain.User;
import com.dating.domain.UserExample;
import com.dating.domain.UserWithBLOBs;
import com.dating.service.base.BaseService;

/**
 * @author elvis
 */
public interface UserService extends BaseService<UserWithBLOBs, UserExample> {
	UserWithBLOBs createUser(UserWithBLOBs user);

	User getCurrentUser(String username);

	Integer deleteCurrentUser(int id);
}
