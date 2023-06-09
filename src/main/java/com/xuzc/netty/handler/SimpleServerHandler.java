package com.xuzc.netty.handler;

import org.apache.zookeeper.server.Request;

import com.alibaba.fastjson.JSONObject;
import com.xuzc.netty.handler.param.ServerRequest;
import com.xuzc.netty.util.Response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {		
		ServerRequest serverRequest =  JSONObject.parseObject(msg.toString(),ServerRequest.class);
		System.out.println(msg.toString());
		Response response = new Response();
		response.setId(serverRequest.getId());
		response.setResult("it is ok");
		ctx.channel().writeAndFlush(JSONObject.toJSONString(response));
		ctx.channel().writeAndFlush("\r\n");
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if(evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent)evt;
			if(event.state().equals(IdleState.READER_IDLE)) {
				System.out.println("No Read");
				ctx.channel().close();
			}else if(event.state().equals(IdleState.WRITER_IDLE)) {
				System.out.println("No Write");
			}else if(event.state().equals(IdleState.ALL_IDLE)) {
				System.out.println("No Read and Write");
				ctx.channel().writeAndFlush("ping\r\n");
			}
		}
	}
	

}
