package com.example.own.server.nettyclient;

import com.example.own.server.bean.LoginRequestPacket;
import com.example.own.server.bean.MessageRequestPacket;
import com.example.own.server.handler.ClientHandler;
import com.example.own.server.handler.MessageResponseHandler;
import com.example.own.server.serializer.PacketCodec;
import com.example.own.server.utils.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public class MessageClient {


    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel channel) {
//                channel.pipeline().addLast(new ClientHandler());
//                channel.pipeline().addLast(new FirstClientHandler());
//                channel.pipeline().addLast(new LifeCycleTestHandler());
                channel.pipeline().addLast(new ClientHandler());
                channel.pipeline().addLast(new MessageResponseHandler());
            }
        });

        connect(bootstrap, "127.0.0.1", 8000, 5);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {

        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {

                Channel channel = ((ChannelFuture)future).channel();
//                startConsoleThread(channel);
                startConsoleThread2(channel);
            }
        });
    }


//    private static void startConsoleThread(Channel channel) {
//        new Thread(() -> {
//            while (!Thread.interrupted()) {
//                if (LoginUtil.hasLogin(channel)) {
//                    System.out.println("输入消息发送至服务器：");
//                    Scanner sc = new Scanner(System.in);
//                    String line = sc.nextLine();
//
//                    MessageRequestPacket packet = new MessageRequestPacket();
//                    packet.setMessage(line);
//                    ByteBuf byteBuf = new PacketCodec().encode(packet);
//                    channel.writeAndFlush(byteBuf);
//                }
//            }
//        }).start();
//    }

    private static void startConsoleThread2(Channel channel) {
        System.out.println("console 连接");
        Scanner scanner = new Scanner(System.in);
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        new Thread(()->{
            while (!Thread.interrupted()) {
                if(!SessionUtil.hasLogin(channel)) {
                    System.out.println("输入用户名登录：");
                    String username = scanner.nextLine();
                    loginRequestPacket.setUsername(username);
                    loginRequestPacket.setUserId(username);
                    loginRequestPacket.setPassword("pwd");

                    channel.writeAndFlush(new PacketCodec().encode(loginRequestPacket));
                    waitForLoginResponse();
                } else {
                    System.out.println("已登录");
                    String toUserId = scanner.next();
                    String message = scanner.next();
                    channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
                    System.out.println("消息已发送");
                }
            }

        }).start();

    }

    private static void waitForLoginResponse() {
        try{
            Thread.sleep(1000);
        } catch (Exception e) {

        }

    }


}
