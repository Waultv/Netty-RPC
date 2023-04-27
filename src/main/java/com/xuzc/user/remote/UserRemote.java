package com.xuzc.user.remote;

import java.util.List;

import com.xuzc.netty.util.Response;
import com.xuzc.user.bean.User;

public interface UserRemote {

	public Response saveUser(User user);
	
	public Response saveUsers(List<User> users);
}
