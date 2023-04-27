package com.xuzc.user.remote;

import java.util.List;

import javax.annotation.Resource;

import com.xuzc.netty.annotation.Remote;
import com.xuzc.netty.util.Response;
import com.xuzc.netty.util.ResponseUtil;
import com.xuzc.user.bean.User;
import com.xuzc.user.service.UserService;

@Remote
public class UserRemoteImpl implements UserRemote{
	@Resource
	private UserService userService;
	
	public Response saveUser(User user) {
		userService.save(user);
		
		return ResponseUtil.createSuccessResult(user);
	}
	
	public Response saveUsers(List<User> users) {
		userService.saveList(users);
		return ResponseUtil.createSuccessResult(users);
	}
}
