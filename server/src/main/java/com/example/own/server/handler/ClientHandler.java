package com.example.own.server.handler;

import com.example.own.server.bean.LoginRequestPacket;
import com.example.own.server.bean.LoginResponsePacket;
import com.example.own.server.bean.MessageResponsePacket;
import com.example.own.server.bean.Packet;
import com.example.own.server.serializer.PacketCodec;
import com.example.own.server.utils.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {


    public void channelActive(ChannelHandlerContext ctx) {

        System.out.println(new Date() + ": ls客户端开始登录");

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("flash");
        loginRequestPacket.setPassword("pwd");

        ByteBuf byteBuf = new PacketCodec().encode(loginRequestPacket);
        ctx.channel().writeAndFlush(byteBuf);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = new PacketCodec().decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.isSuccess()) {
                System.out.println(new Date() + ": ls客户端登录成功");
                LoginUtil.markAsLogin(ctx.channel());
            } else {
                System.out.println(new Date() + "：ls客户端登录失败" + loginResponsePacket.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + "收到服务端消息： "+ messageResponsePacket.getMessage());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭");

    }
}
