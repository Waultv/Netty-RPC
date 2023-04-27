package com.xuzc.netty;

import org.junit.Test;

import com.xuzc.netty.client.ClientRequest;
import com.xuzc.netty.client.NettyTCPClient;
import com.xuzc.netty.util.Response;
import com.xuzc.user.bean.User;

public class TCPtest {
	@Test
	public void testGetResponse() {
		ClientRequest request = new ClientRequest();
		request.setContent("测试长连接请求");
		Response response = NettyTCPClient.send(request);
		System.out.println(response.getResult());
	}
	
	@Test
	public void testsaveuser() {
		ClientRequest request = new ClientRequest();
		
		User user = new User();
		user.setId(1);
		user.setName("xuzc");
		request.setCommand("com.xuzc.user.controller.UserController.saveUser");
		request.setContent(user);
		Response response = NettyTCPClient.send(request);
		System.out.println(response.getResult());
	}
}
