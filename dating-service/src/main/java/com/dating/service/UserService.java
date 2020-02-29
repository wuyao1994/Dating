package com.dating.service;

import com.dating.domain.UserExample;
import com.dating.domain.UserWithBLOBs;
import com.dating.service.base.BaseService;

/**
 * @author elvis
 */
public interface UserService extends BaseService<UserWithBLOBs, UserExample> {
    public UserWithBLOBs createUser(UserWithBLOBs user);
}
