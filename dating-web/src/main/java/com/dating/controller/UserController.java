package com.dating.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dating.base.UserResult;
import com.dating.constants.SecurityConstants;
import com.dating.constants.UserResultConstant;
import com.dating.domain.*;
import com.dating.request.UserRequest;
import com.dating.service.UserCollectionService;
import com.dating.service.UserLikeService;
import com.dating.service.UserService;
import com.dating.util.JwtTokenUtils;

/**
 * @author elvis
 */
@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	UserCollectionService userCollectionService;

	@Autowired
	UserLikeService userLikeService;

	@Autowired
	HttpServletRequest request;

	@PostMapping("/index")
	@ResponseBody
	public Object getUsers(@RequestBody(required = false) UserRequest userRequest) {
		// get current user
		String token = request.getHeader("Authorization")
				.replace(SecurityConstants.TOKEN_PREFIX, "");
		String userName = JwtTokenUtils.getUsernameByToken(token);
		User currentUser = userService.getCurrentUser(userName);

		final String sex = currentUser.getSex();
		List<UserWithBLOBs> users = new ArrayList<>();
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeleteIsNull();
		if ("male".equals(sex)) {
			criteria.andSexEqualTo("female");
		}
		else {
			criteria.andSexEqualTo("male");
		}
		if (userRequest != null) {
			final int pageNum = userRequest.getPageNum();
			final int pageSize = userRequest.getPageSize();
			final String location = userRequest.getLocation();
			final int start_age = userRequest.getStart_age();
			final int end_age = userRequest.getEnd_age();
			if (start_age > 0 && end_age > 0) {
				criteria.andAgeBetween(start_age, end_age);
			}
			users = userService.selectByExampleWithBLOBsForStartPage(example, pageNum,
					pageSize);
		}
		else {
			users = userService.selectByExampleWithBLOBsForStartPage(example, 1, 20);
		}
		if (users == null) {
			return new UserResult(UserResultConstant.FAILED, "load user failed");
		}
		return new UserResult(UserResultConstant.SUCCESS, users);
	}

	@PostMapping("/update/{id}")
	@ResponseBody
	public Object updateCurrentUser(@PathVariable("id") int id,
			@RequestBody UserWithBLOBs user) {
		user.setId(id);
		int count = userService.updateByPrimaryKeySelective(user);
		return new UserResult(UserResultConstant.SUCCESS,
				"update user profile info success");
	}

	@PostMapping("/delete/{id}")
	@ResponseBody
	public Object deleteCurrentUser(@PathVariable("id") int id) {
		userService.deleteCurrentUser(id);
		return new UserResult(UserResultConstant.SUCCESS, "delete user success");
	}

	@PostMapping("/{id}")
	@ResponseBody
	public Object getUser(@PathVariable("id") int id) {
		UserWithBLOBs user = userService.selectByPrimaryKey(id);
		return new UserResult(UserResultConstant.SUCCESS, user);
	}

	@PostMapping("/collection/{id}")
	@ResponseBody
	public Object collectionUser(@PathVariable("id") int id) {
		// get current user
		String token = request.getHeader("Authorization")
				.replace(SecurityConstants.TOKEN_PREFIX, "");
		String userName = JwtTokenUtils.getUsernameByToken(token);
		User currentUser = userService.getCurrentUser(userName);

		UserCollection userCollection = new UserCollection();
		userCollection.setUserId(currentUser.getId());
		userCollection.setCollectionUserId(id);
		int count = userCollectionService.insert(userCollection);
		if (count > 0) {
			return new UserResult(UserResultConstant.SUCCESS, "collection user success");
		}
		return new UserResult(UserResultConstant.FAILED, "collection user failed");
	}

	@RequestMapping(value = "/collection/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Object cancelCollectionUser(@PathVariable("id") int id) {
		// get current user
		String token = request.getHeader("Authorization")
				.replace(SecurityConstants.TOKEN_PREFIX, "");
		String userName = JwtTokenUtils.getUsernameByToken(token);
		User currentUser = userService.getCurrentUser(userName);
		UserCollectionExample example = new UserCollectionExample();
		example.createCriteria().andCollectionUserIdEqualTo(id).andUserIdEqualTo(currentUser.getId());
		int count = userCollectionService.deleteByExample(example);
		if (count > 0) {
			return new UserResult(UserResultConstant.SUCCESS, "delete user collection success");
		}
		return new UserResult(UserResultConstant.FAILED, "delete user collection failed");
	}

	@PostMapping("/collection/list")
	@ResponseBody
	public Object getCurrentUserCollectionList() {
		// get current user
		String token = request.getHeader("Authorization")
				.replace(SecurityConstants.TOKEN_PREFIX, "");
		String userName = JwtTokenUtils.getUsernameByToken(token);
		User currentUser = userService.getCurrentUser(userName);

		UserCollectionExample example = new UserCollectionExample();
		example.createCriteria().andUserIdEqualTo(currentUser.getId());
		List<UserCollection> userCollectionList = userCollectionService.selectByExample(example);
		UserExample userExample = new UserExample();
		UserExample.Criteria criteria = userExample.createCriteria();
		criteria.andIsDeleteIsNull().andIdIn(userCollectionList.stream().map(UserCollection::getCollectionUserId).collect(Collectors.toList()));
		List<UserWithBLOBs> userList = userService.selectByExample(userExample);
		return new UserResult(UserResultConstant.SUCCESS, userList);
	}

	@PostMapping("/like/{id}")
	@ResponseBody
	public Object likeUser(@PathVariable("id") int id) {
		// get current user
		String token = request.getHeader("Authorization")
				.replace(SecurityConstants.TOKEN_PREFIX, "");
		String userName = JwtTokenUtils.getUsernameByToken(token);
		User currentUser = userService.getCurrentUser(userName);

		UserLike userLike = new UserLike();
		userLike.setUserId(currentUser.getId());
		userLike.setLikeUserId(id);

		int count = userLikeService.insert(userLike);
		if (count > 0) {
			UserLikeExample userLikeExample = new UserLikeExample();
			userLikeExample.createCriteria().andUserIdEqualTo(id).andLikeUserIdEqualTo(currentUser.getId());
			int datingCount = userLikeService.countByExample(userLikeExample);
			if (datingCount > 0) {
				UserWithBLOBs user = userService.selectByPrimaryKey(id);
				return new UserResult(UserResultConstant.MATCH_SUCCESS, user);
			}
		}
		return new UserResult(UserResultConstant.SUCCESS, "like success");
	}

	@DeleteMapping("/like/{id}")
	@ResponseBody
	public Object dislikeUser(@PathVariable("id") int id) {
		// get current user
		String token = request.getHeader("Authorization")
				.replace(SecurityConstants.TOKEN_PREFIX, "");
		String userName = JwtTokenUtils.getUsernameByToken(token);
		User currentUser = userService.getCurrentUser(userName);

		UserLikeExample userLikeExample = new UserLikeExample();
		userLikeExample.createCriteria().andUserIdEqualTo(currentUser.getId()).andLikeUserIdEqualTo(id);
		int count = userLikeService.deleteByExample(userLikeExample);
		if (count > 0) {
			return new UserResult(UserResultConstant.SUCCESS, "delete like user success");
		}
		return new UserResult(UserResultConstant.FAILED, "delete like user failed");
	}

	@PostMapping("/like/myLikeList")
	@ResponseBody
	public Object getMyLikeList() {
		// get current user
		String token = request.getHeader("Authorization")
				.replace(SecurityConstants.TOKEN_PREFIX, "");
		String userName = JwtTokenUtils.getUsernameByToken(token);
		User currentUser = userService.getCurrentUser(userName);

		UserLikeExample userLikeExample = new UserLikeExample();
		userLikeExample.createCriteria().andUserIdEqualTo(currentUser.getId());
		List<UserLike> userLikeList = userLikeService.selectByExample(userLikeExample);
		if (userLikeList.size() > 0) {
			UserExample example = new UserExample();
			example.createCriteria().andIdIn(userLikeList.stream().map(UserLike::getLikeUserId).collect(Collectors.toList()));
			List<UserWithBLOBs> users = userService.selectByExampleWithBLOBs(example);
			if (users.size() > 0) {
				return new UserResult(UserResultConstant.SUCCESS, users);
			}
		}
		return new UserResult(UserResultConstant.SUCCESS, "my like list empty");
	}

	@PostMapping("/like/likeMeList")
	@ResponseBody
	public Object getLikeMelist() {
		// get current user
		String token = request.getHeader("Authorization")
				.replace(SecurityConstants.TOKEN_PREFIX, "");
		String userName = JwtTokenUtils.getUsernameByToken(token);
		User currentUser = userService.getCurrentUser(userName);

		UserLikeExample userLikeExample = new UserLikeExample();
		userLikeExample.createCriteria().andLikeUserIdEqualTo(currentUser.getId());
		List<UserLike> likeMeUserList = userLikeService.selectByExample(userLikeExample);
		if (likeMeUserList.size() > 0) {
			UserExample example = new UserExample();
			example.createCriteria().andIdIn(likeMeUserList.stream().map(UserLike::getUserId).collect(Collectors.toList()));
			List<UserWithBLOBs> users = userService.selectByExampleWithBLOBs(example);
			if (users.size() > 0) {
				return new UserResult(UserResultConstant.SUCCESS, users);
			}
		}
		return new UserResult(UserResultConstant.SUCCESS, "like list empty");
	}
}
