package com.example.own.server.nettyserver;

import com.example.own.server.handler.FirstServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @desc: netty服务器
 * @author:英布
 * @date: 2023-05-28 16:38:42
 *
 */
public class NettyServer {


    public static void main(String[] args) {

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<NioSocketChannel>() {

            protected void initChannel(NioSocketChannel ch) {
//                ch.pipeline().addLast(new StringDecoder());
//                ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
//                    @Override
//                    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
//                        System.out.println(msg);
//                    }
//                });
                ch.pipeline().addLast(new FirstServerHandler());
            }
        });

        serverBootstrap.bind(8000).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口绑定成功");
                } else {
                    System.out.println("端口绑定失败");
                }
            }
        });

//        bind(serverBootstrap, 3860);
    }


    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功");
                } else {
                    System.err.println("端口[" + port + "]绑定失败！");
                    bind(serverBootstrap,port +1);
                }
            }
        });
    }




}
