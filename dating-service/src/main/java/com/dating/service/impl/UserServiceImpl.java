package com.dating.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dating.annotation.BaseService;
import com.dating.dao.UserMapper;
import com.dating.domain.User;
import com.dating.domain.UserExample;
import com.dating.service.UserService;

/**
 * @author elvis
 */
@Service
@Transactional
@BaseService
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User, UserExample>
		implements UserService {
}
