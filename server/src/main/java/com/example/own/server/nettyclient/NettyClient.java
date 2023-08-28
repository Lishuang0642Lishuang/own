package com.example.own.server.nettyclient;

import com.example.own.server.handler.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @desc: netty客户端
 * @author:英布
 * @date: 2023-05-28 16:48:47
 *
 */
public class NettyClient {

    private static final Integer MAX_RETRY = 5;


    public static void main(String[] args) throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel ch) {
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(new FirstClientHandler());
            }
        });

//        bootstrap.connect("juejin.cn", 80).addListener(future -> {
//            if (future.isSuccess()) {
//                System.out.println("连接juejin成功");
//            } else {
//                System.out.println("连接juejin失败");
//            }
//
//        });

//        connect(bootstrap, "meituan.com", 80, MAX_RETRY);

        Channel channel = bootstrap.connect("127.0.0.1", 8000).channel();
//        while (true) {
//            channel.writeAndFlush(new Date() + ": hello world!");
//            Thread.sleep(2000);
//        }
    }


    /**
     * 重连的方法*
     * @param bootstrap
     * @param host
     * @param port
     * @param retry
     */
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        retry--;
        int finalRetry = retry;
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接meituan成功");
            } else if( finalRetry == 0) {
                System.out.println("重试次数已用完");
            } else {
                int order = (MAX_RETRY - finalRetry);
                int delay = 1 << order;
                System.out.println(new Date() + ": 连接meituan失败，第" + order + "次重连");
                //定时任务的逻辑
                bootstrap.config().group().schedule( () -> connect(bootstrap, host, port, finalRetry), delay, TimeUnit.SECONDS);
            }
        });
    }






}
