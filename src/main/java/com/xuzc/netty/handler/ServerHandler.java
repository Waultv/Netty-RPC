package com.xuzc.netty.handler;

import org.apache.zookeeper.server.Request;
import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;

import com.alibaba.fastjson.JSONObject;
import com.xuzc.netty.handler.param.ServerRequest;
import com.xuzc.netty.medium.Media;
import com.xuzc.netty.util.Response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {	
		System.out.println("serverHandler:"+msg.toString());
		ServerRequest serverRequest =  JSONObject.parseObject(msg.toString(),ServerRequest.class);
		Media media = Media.newInstance();
		Response res = media.process(serverRequest);
//		
//		Response response = new Response();
//		response.setId(serverRequest.getId());
//		response.setResult("it is ok");
		ctx.channel().writeAndFlush(JSONObject.toJSONString(res));
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
