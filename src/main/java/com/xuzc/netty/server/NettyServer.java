package com.xuzc.netty.server;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;

import com.xuzc.netty.constants.Constants;
import com.xuzc.netty.factory.ZookeeperFactory;
import com.xuzc.netty.handler.SimpleServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;

public class NettyServer {
	public static void main(String[] args) throws InterruptedException {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
		try {
		ServerBootstrap sb = new ServerBootstrap();

        sb.group(parentGroup, childGroup);
        sb.option(ChannelOption.SO_BACKLOG, 128)//允许128个通道排队
        .childOption(ChannelOption.SO_KEEPALIVE, false)//心跳检测
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<SocketChannel>() {
        	@Override
        	public void initChannel(SocketChannel ch) throws Exception {
        		ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE,Delimiters.lineDelimiter()[0]));
        		ch.pipeline().addLast(new StringDecoder());
        		ch.pipeline().addLast(new IdleStateHandler(60,45,20,TimeUnit.SECONDS));
        		ch.pipeline().addLast(new SimpleServerHandler());
        		ch.pipeline().addLast(new StringEncoder());
        	}
		});
        ChannelFuture f = sb.bind(8080).sync();
        CuratorFramework client = ZookeeperFactory.create();
        InetAddress netAddress = InetAddress.getLocalHost();
        client.create().withMode(CreateMode.EPHEMERAL).forPath(Constants.SERVER_PATH + netAddress.getHostAddress());
        f.channel().closeFuture();
		}
		catch (Exception e) {
			e.printStackTrace();
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
		}
	}
}
