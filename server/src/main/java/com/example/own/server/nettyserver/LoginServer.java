package com.example.own.server.nettyserver;

import com.example.own.server.handler.LoginRequestHandler;
import com.example.own.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class LoginServer {

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<NioSocketChannel>() {


            protected void initChannel(NioSocketChannel ch) {
//                ch.pipeline().addLast(new ServerHandler());
//                ch.pipeline().addLast(new FirstServerHandler());
//                ch.pipeline().addLast(new LifeCycleTestHandler());
//                ch.pipeline().addLast(new InBoundHandlerA());
//                ch.pipeline().addLast(new InBoundHandlerB());
//                ch.pipeline().addLast(new InBoundHandlerC());
//                ch.pipeline().addLast(new OutBoundHandlerA());
//                ch.pipeline().addLast(new OutBoundHandlerB());
//                ch.pipeline().addLast(new OutBoundHandlerC());
                ch.pipeline().addLast(new MessageRequestHandler());
                ch.pipeline().addLast(new LoginRequestHandler());
            }
        });

        serverBootstrap.bind(8000).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口绑定成功");
                    System.out.println();
                } else {
                    System.out.println("端口绑定失败");
                }
            }
        });
    }
}
