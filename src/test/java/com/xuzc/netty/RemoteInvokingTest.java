package com.xuzc.netty;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xuzc.netty.annotation.RemoteInvoke;
import com.xuzc.netty.client.ClientRequest;
import com.xuzc.netty.client.NettyTCPClient;
import com.xuzc.netty.util.Response;
import com.xuzc.user.bean.User;
import com.xuzc.user.remote.UserRemote;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RemoteInvokingTest.class)
@ComponentScan("com.xuzc")
public class RemoteInvokingTest {

	@RemoteInvoke
	private UserRemote userRemote;
	
	
	@Test
	public void testsaveuser() {
		User user = new User();
		user.setId(1);
		user.setName("xuzc");
		userRemote.saveUser(user);
	}
	
	
	@Test
	public void testsaveusers() {
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setId(2);
		user.setName("VAULT");
		users.add(user);
		userRemote.saveUsers(users);
	}
}
