package com.dating.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dating.annotation.BaseService;
import com.dating.dao.UserCollectionMapper;
import com.dating.domain.UserCollection;
import com.dating.domain.UserCollectionExample;
import com.dating.service.UserCollectionService;

@Service
@Transactional
@BaseService
public class UserCollectionServiceImpl extends
		BaseServiceImpl<UserCollectionMapper, UserCollection, UserCollectionExample>
		implements UserCollectionService {
}
