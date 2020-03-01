package com.dating.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dating.annotation.BaseService;
import com.dating.dao.UserLikeMapper;
import com.dating.domain.UserLike;
import com.dating.domain.UserLikeExample;
import com.dating.service.UserLikeService;

/**
 * @author elvis
 */

@Service
@Transactional
@BaseService
public class UserLikeServiceImpl
		extends BaseServiceImpl<UserLikeMapper, UserLike, UserLikeExample>
		implements UserLikeService {
}
