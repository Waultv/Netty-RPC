package com.xuzc.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println(msg.toString());
    	if("ping".equals(msg.toString())) {
    		ctx.channel().writeAndFlush("ping\r\n");
    		return;
    	}
        ctx.channel().attr(AttributeKey.valueOf("wault")).set(msg);;;
//        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    }
}
